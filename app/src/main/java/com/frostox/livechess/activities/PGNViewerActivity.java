package com.frostox.livechess.activities;

import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frostox.livechess.R;
import com.frostox.livechess.adapters.GridAdapter;
import com.frostox.livechess.customviews.Board;
import com.frostox.livechess.entities.Rank;
import com.frostox.livechess.entities.Round;
import com.frostox.livechess.wrappers.SQIWrapper;
import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import chesspresso.game.Game;
import chesspresso.move.Move;
import chesspresso.pgn.PGNReader;
import chesspresso.pgn.PGNSyntaxError;
import chesspresso.position.Position;

public class PGNViewerActivity extends AppCompatActivity {


    GridAdapter adapter;

    String whitePlayer, blackPlayer, whiteRTG, blackRTG, tableNo;
    TextView whitePlayerTxt, blackPlayerTxt, whiteRTGTxt, blackRTGTxt;
    TextView result, tableNoTxt;

    String tournamentId, roundId;

    Game game;

    ImageView backward, play, forward;
    FlexboxLayout pgnViewer;

    Board board;

    FirebaseDatabase database;
    DatabaseReference roundRef;

    private int currentPly = 0;

    private boolean liveMode = true;
    private boolean playingMode = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pgnviewer);

        board = (Board) findViewById(R.id.board);
        whitePlayerTxt = (TextView) findViewById(R.id.game_item_p1_name);
        blackPlayerTxt = (TextView) findViewById(R.id.game_item_p2_name);
        whiteRTGTxt = (TextView) findViewById(R.id.game_item_p1_rtg);
        blackRTGTxt = (TextView) findViewById(R.id.game_item_p2_rtg);
        tableNoTxt = (TextView) findViewById(R.id.game_item_table_no);
        pgnViewer = (FlexboxLayout) findViewById(R.id.flexbox);
        pgnViewer.setFlexDirection(FlexboxLayout.FLEX_DIRECTION_ROW);
        result = (TextView) findViewById(R.id.game_item_p1_name7);


        View view = pgnViewer.getChildAt(0);
        FlexboxLayout.LayoutParams lp = (FlexboxLayout.LayoutParams) view.getLayoutParams();
        lp.order = -1;
        lp.flexGrow = 2;
        view.setLayoutParams(lp);

        whitePlayer = getIntent().getStringExtra("whitePlayer");
        blackPlayer = getIntent().getStringExtra("blackPlayer");
        whiteRTG = getIntent().getStringExtra("whitePlayerRTG");
        blackRTG = getIntent().getStringExtra("blackPlayerRTG");
        tableNo = getIntent().getStringExtra("tableNo");

        whitePlayerTxt.setText(whitePlayer);
        blackPlayerTxt.setText(blackPlayer);
        whiteRTGTxt.setText(whiteRTG);
        blackRTGTxt.setText(blackRTG);
        tableNoTxt.setText(tableNo);

        tournamentId = getIntent().getStringExtra("tournamentKey");
        roundId = getIntent().getStringExtra("roundKey");
        database = FirebaseDatabase.getInstance();
        roundRef = database.getReference("tournaments").child(tournamentId).child("rounds").child(roundId);

        roundRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Round round = dataSnapshot.getValue(Round.class);

                StringReader sReader = new StringReader(round.getPgn());
                PGNReader pgnReader = new PGNReader(sReader, "name");
                Game game;
                try {
                    while((game = pgnReader.parseGame()) != null){
                        if(game.getWhite().equals(whitePlayer) &&
                                game.getBlack().equals(blackPlayer)){
                            PGNViewerActivity.this.game = game;
                            setUpGame();



                        }
                    }
                } catch (PGNSyntaxError pgnSyntaxError) {
                    pgnSyntaxError.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(PGNViewerActivity.class.getName(), databaseError.getDetails());
            }
        });


    }

    public void playToCurrent(){

        if(!liveMode) return;

        setUpPgnViewer();

        List<SQIWrapper> sqis = new ArrayList<>();
        for(int i=0; i<64; i++)
            sqis.add(new SQIWrapper(i+1));
        adapter = new GridAdapter(this, this.getLayoutInflater(), sqis);
        adapter.flipBoard();
        board.setAdapter(adapter);

        if(game == null) {
            return;
        }


        game.goBackToLineBegin();

        if(liveMode) {
            currentPly = 0;
            delay();
        }


    }

    public void delay(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(game.goForward() && playingMode){
                    currentPly++;
                    updateGame();
                } else playingMode = false;
            }
        }, 200);
    }

    public void updateGame(){
        if(pgnViewer.getChildCount() != 0)
            ((TextView) pgnViewer.getChildAt(currentPly - 1)).setTypeface(null, Typeface.BOLD);
        if(currentPly - 2 >= 0 && pgnViewer.getChildCount() != 0)
            ((TextView) pgnViewer.getChildAt(currentPly - 2)).setTypeface(null, Typeface.NORMAL);



        Position position = game.getPosition();
        boolean flipped = false;

        int fromSqi = -1, toSqi = -1;
        Move move = position.getLastMove();
        if(move != null){
            fromSqi = position.getLastMove().getFromSqi();
            toSqi = position.getLastMove().getToSqi();
        }

        for (int i = 0; i < 64; i++) {

            int pieceSqi = i;

            if(pieceSqi == fromSqi){
                adapter.setPieceToSqi(position.getPiece(i), position.getColor(i), i, true);
            }
            else if(pieceSqi == toSqi){
                adapter.setPieceToSqi(position.getPiece(i), position.getColor(i), i, false);
            }
            else
                adapter.setPieceToSqi(position.getPiece(i), position.getColor(i), i, null);

        }
        result.setText(game.getResultStr());
        adapter.notifyDataSetChanged();


        delay();
    }


    public void setUpGame(){

        if(playingMode) return;

        setUpPgnViewer();

        List<SQIWrapper> sqis = new ArrayList<>();
        for(int i=0; i<64; i++)
            sqis.add(new SQIWrapper(i+1));
        adapter = new GridAdapter(this, this.getLayoutInflater(), sqis);
        adapter.flipBoard();
        board.setAdapter(adapter);

        if(game == null) {
            return;
        }


        game.goBackToLineBegin();

        if(liveMode){
            currentPly = 0;
            while(game.goForward()){currentPly++;}
        } else {
            int i = 0;
            while(i<currentPly && game.goForward()){
                i++;
            }
        }

        if(pgnViewer.getChildCount() != 0 && (currentPly-1) >= 0)
            ((TextView) pgnViewer.getChildAt(currentPly - 1)).setTypeface(null, Typeface.BOLD);



        Position position = game.getPosition();
        boolean flipped = false;

        int fromSqi = -1, toSqi = -1;
        Move move = position.getLastMove();
        if(move != null){
            fromSqi = position.getLastMove().getFromSqi();
            toSqi = position.getLastMove().getToSqi();
        }

        for (int i = 0; i < 64; i++) {

            int pieceSqi = i;

            if(pieceSqi == fromSqi){
                adapter.setPieceToSqi(position.getPiece(i), position.getColor(i), i, true);
            }
            else if(pieceSqi == toSqi){
                adapter.setPieceToSqi(position.getPiece(i), position.getColor(i), i, false);
            }
            else
                adapter.setPieceToSqi(position.getPiece(i), position.getColor(i), i, null);

        }
        result.setText(game.getResultStr());
        adapter.notifyDataSetChanged();
    }

    public void setUpPgnViewer(){
        pgnViewer.removeAllViews();
        game.goBackToLineBegin();
        final List<String> moves = new ArrayList<>();
        Position position;
        int i = 0;
        while(game.goForward()){
            i++;
            position = game.getPosition();
            Move move = position.getLastMove();
            if(move == null) continue;
            moves.add(move.getSAN());

            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.text_view, null);

            if(i%2!=0){
                textView.setText(((i/2) + 1) + ". " + move.getSAN());
            } else
                textView.setText(move.getSAN());
            final String textMove = move.getSAN();
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(playingMode) return;
                    liveMode = false;
                    ((ImageView) findViewById(R.id.play_button)).setImageResource(R.drawable.ic_play_button);
                    currentPly = moves.indexOf(textMove) + 1;
                    setUpGame();
                }
            });
            pgnViewer.addView(textView);
        }



        game.goBackToLineBegin();
    }

    public void next(View view){
        playingMode = false;
        liveMode = false;
        setUpGame();
        if(game.goForward() && adapter!=null){
            ((ImageView) findViewById(R.id.play_button)).setImageResource(R.drawable.ic_play_button);
            liveMode = false;
            if(currentPly>0)
            ((TextView) pgnViewer.getChildAt(currentPly - 1)).setTypeface(null, Typeface.NORMAL);
            currentPly++;
            ((TextView) pgnViewer.getChildAt(currentPly - 1)).setTypeface(null, Typeface.BOLD);
            Position position = game.getPosition();
            int fromSqi, toSqi;
            fromSqi = position.getLastMove().getFromSqi();
            toSqi = position.getLastMove().getToSqi();
            for (int i = 0; i < 64; i++) {

                int pieceSqi = i;

                if(pieceSqi == fromSqi){
                    adapter.setPieceToSqi(position.getPiece(i), position.getColor(i), i, true);
                }
                else if(pieceSqi == toSqi){
                    adapter.setPieceToSqi(position.getPiece(i), position.getColor(i), i, false);
                }
                else
                    adapter.setPieceToSqi(position.getPiece(i), position.getColor(i), i, null);

            }

            adapter.notifyDataSetChanged();
        }

    }

    public void prev(View view){
        playingMode = false;
        liveMode = false;
        setUpGame();
        if(game.goBack() && adapter!=null){
            ((ImageView) findViewById(R.id.play_button)).setImageResource(R.drawable.ic_play_button);
            liveMode = false;
            ((TextView) pgnViewer.getChildAt(currentPly - 1)).setTypeface(null, Typeface.NORMAL);
            currentPly--;
            if(currentPly>0)
            ((TextView) pgnViewer.getChildAt(currentPly - 1)).setTypeface(null, Typeface.BOLD);
            Position position = game.getPosition();

            int fromSqi = -1, toSqi = -1;
            if(position.getLastMove()!=null){
                fromSqi = position.getLastMove().getFromSqi();
                toSqi = position.getLastMove().getToSqi();
            }
            for (int i = 0; i < 64; i++) {

                int pieceSqi = i;

                if(pieceSqi == fromSqi){
                    adapter.setPieceToSqi(position.getPiece(i), position.getColor(i), i, true);
                }
                else if(pieceSqi == toSqi){
                    adapter.setPieceToSqi(position.getPiece(i), position.getColor(i), i, false);
                }
                else
                    adapter.setPieceToSqi(position.getPiece(i), position.getColor(i), i, null);

            }

            adapter.notifyDataSetChanged();
        }
    }

    public void playPause(View view){
        if(liveMode){
            liveMode = false;
            playingMode = false;
            ((ImageView) findViewById(R.id.play_button)).setImageResource(R.drawable.ic_play_button);
        } else {
            ((ImageView) findViewById(R.id.play_button)).setImageResource(R.drawable.ic_pause1);
            liveMode = true;
            playingMode = true;
            playToCurrent();
        }
    }
}
