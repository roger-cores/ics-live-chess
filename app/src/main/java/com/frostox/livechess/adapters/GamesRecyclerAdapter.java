package com.frostox.livechess.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frostox.livechess.R;
import com.frostox.livechess.entities.GameEntity;
import com.frostox.livechess.viewholders.GamesViewHolder;
import com.frostox.livechess.wrappers.SQIWrapper;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import chesspresso.game.Game;
import chesspresso.pgn.PGNReader;
import chesspresso.pgn.PGNSyntaxError;
import chesspresso.position.Position;

/**
 * Created by roger on 10/27/2016.
 */
public class GamesRecyclerAdapter extends RecyclerView.Adapter<GamesViewHolder> {

    private List<GameEntity> games;
    private Activity activity;

    public GamesRecyclerAdapter(Activity activity, List<GameEntity> games) {
        this.activity = activity;
        this.games = games;
    }

    @Override
    public GamesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
        return new GamesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GamesViewHolder holder, int position) {
        GameEntity game = games.get(position);
        setUpGame(holder, game);

    }

    @Override
    public int getItemCount() {
        if(games != null){
            return games.size();
        } else return 0;
    }

    public void setUpGame(GamesViewHolder holder, GameEntity gameEntity){
        List<SQIWrapper> sqis = new ArrayList<>();
        for(int i=0; i<64; i++)
            sqis.add(new SQIWrapper(i+1));
        GridAdapter adapter = new GridAdapter(activity, activity.getLayoutInflater(), sqis);
        holder.getBoard().setAdapter(adapter);

        StringReader sReader = new StringReader(gameEntity.getPgn());
        PGNReader pReader = new PGNReader(sReader, "name");
        Game game = null;
        try {
            game = null;
            game = pReader.parseGame();
            adapter.setGame(game);
        } catch (PGNSyntaxError pgnSyntaxError) {
            pgnSyntaxError.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(game == null) {
            return;
        }


        game.goBackToLineBegin();

        Position position = game.getPosition();
        boolean flipped = false;

//        if(position.getToPlay() == 0){
//            flipped = true;
//        } else {
//            flipped = false;
//        }
//
//        if(!flipped) {

            for (int i = 0; i < 64; i++) {
                adapter.setPieceToSqi(position.getPiece(i), position.getColor(i), i);

            }

//            flipIndexes();
//        }
//
//        else {
//
//            for (int i = 63; i >= 0; i--) {
//                adapter.setPieceToSqi(position.getPiece(i), position.getColor(i), i);
//
//            }
//
//            orderIndexes();
//
//        }

        adapter.notifyDataSetChanged();

    }

//    public void flipIndexes(){
//        ((TextView) left.findViewById(R.id.one)).setText("8");
//        ((TextView) left.findViewById(R.id.two)).setText("7");
//        ((TextView) left.findViewById(R.id.three)).setText("6");
//        ((TextView) left.findViewById(R.id.four)).setText("5");
//        ((TextView) left.findViewById(R.id.five)).setText("4");
//        ((TextView) left.findViewById(R.id.six)).setText("3");
//        ((TextView) left.findViewById(R.id.seven)).setText("2");
//        ((TextView) left.findViewById(R.id.eight)).setText("1");
//
//
//        ((TextView) bottom.findViewById(R.id.a)).setText("h");
//        ((TextView) bottom.findViewById(R.id.b)).setText("g");
//        ((TextView) bottom.findViewById(R.id.c)).setText("f");
//        ((TextView) bottom.findViewById(R.id.d)).setText("e");
//        ((TextView) bottom.findViewById(R.id.e)).setText("d");
//        ((TextView) bottom.findViewById(R.id.f)).setText("c");
//        ((TextView) bottom.findViewById(R.id.g)).setText("b");
//        ((TextView) bottom.findViewById(R.id.h)).setText("a");
//    }
//
//    public void orderIndexes(){
//        ((TextView) left.findViewById(R.id.one)).setText("1");
//        ((TextView) left.findViewById(R.id.two)).setText("2");
//        ((TextView) left.findViewById(R.id.three)).setText("3");
//        ((TextView) left.findViewById(R.id.four)).setText("4");
//        ((TextView) left.findViewById(R.id.five)).setText("5");
//        ((TextView) left.findViewById(R.id.six)).setText("6");
//        ((TextView) left.findViewById(R.id.seven)).setText("7");
//        ((TextView) left.findViewById(R.id.eight)).setText("8");
//
//
//        ((TextView) bottom.findViewById(R.id.a)).setText("a");
//        ((TextView) bottom.findViewById(R.id.b)).setText("b");
//        ((TextView) bottom.findViewById(R.id.c)).setText("c");
//        ((TextView) bottom.findViewById(R.id.d)).setText("d");
//        ((TextView) bottom.findViewById(R.id.e)).setText("e");
//        ((TextView) bottom.findViewById(R.id.f)).setText("f");
//        ((TextView) bottom.findViewById(R.id.g)).setText("g");
//        ((TextView) bottom.findViewById(R.id.h)).setText("h");
//    }
}
