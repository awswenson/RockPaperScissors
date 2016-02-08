package com.cs407_android.rockpaperscissors;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayFragment extends Fragment {

    private static final String ARG_PLAYER_ONE = "player1";
    private static final String ARG_PLAYER_TWO = "player2";

    private String player1Choice;
    private String player2Choice;

    private Button rockButton;
    private Button paperButton;
    private Button scissorsButton;
    private TextView headerTextView;

    /**
     * DONT CHANGE THIS METHOD
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param player1Choice Player 1's move (null if not specified yet).
     * @param player2Choice Player 2's move (null if not specified yet).
     * @return A new instance of fragment PlayFragment.
     */
    public static PlayFragment newInstance(String player1Choice, String player2Choice) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLAYER_ONE, player1Choice);
        args.putString(ARG_PLAYER_TWO, player2Choice);
        fragment.setArguments(args);
        return fragment;
    }

    public PlayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            player1Choice = getArguments().getString(ARG_PLAYER_ONE);
            player2Choice = getArguments().getString(ARG_PLAYER_TWO);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //TA Implementation DON'T CHANGE
        View view = null;
        view = inflater.inflate(R.layout.fragment_play, container, false);

        //instantiate widgets
        rockButton = (Button) view.findViewById(R.id.rock);
        paperButton = (Button) view.findViewById(R.id.paper);
        scissorsButton = (Button) view.findViewById(R.id.scissors);
        headerTextView = (TextView) view.findViewById(R.id.header);

        //set header
        if(player1Choice == null) {
            headerTextView.setText(getString(R.string.player_1_header));
        }
        else{
            headerTextView.setText(getString(R.string.player_2_header));
        }

        return view;

        // End TA Implementation
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TA Implementation

        //different way of implementing click interaction.
        rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(player1Choice == null) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_container, PlayFragment.newInstance(getResources().getString(R.string.rock), null))
                            .addToBackStack(null)
                            .commit();
                }
                else{
                    displayWinner(decideWinner(player1Choice, getResources().getString(R.string.rock)));
                }
            }
        });

        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(player1Choice == null) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_container, PlayFragment.newInstance(getResources().getString(R.string.paper), null))
                            .addToBackStack(null)
                            .commit();
                }
                else{
                    displayWinner(decideWinner(player1Choice, getResources().getString(R.string.paper)));
                }
            }
        });

        scissorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(player1Choice == null) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_container, PlayFragment.newInstance(getResources().getString(R.string.scissors), null))
                            .addToBackStack(null)
                            .commit();
                }
                else{
                    displayWinner(decideWinner(player1Choice, getResources().getString(R.string.scissors)));
                }
            }
        });
    }

    private String decideWinner(String player1Choice, String player2Choice) {
        if (player1Choice == null || player2Choice == null) {
            return "ERROR";
        }

        if (player1Choice.equals(getResources().getString(R.string.rock))) { // Player one chose rock
            if (player2Choice.equals(getResources().getString(R.string.rock))) {
                return "tie";
            }
            else if (player2Choice.equals(getResources().getString(R.string.paper))) {
                return ARG_PLAYER_TWO;
            }
            else {
                return ARG_PLAYER_ONE;
            }
        }
        else if (player1Choice.equals(getResources().getString(R.string.paper))) { // Player one chose paper
            if (player2Choice.equals(getResources().getString(R.string.rock))) {
                return ARG_PLAYER_ONE;
            }
            else if (player2Choice.equals(getResources().getString(R.string.paper))) {
                return "tie";
            }
            else {
                return ARG_PLAYER_TWO;
            }
        }
        else { // Player one chose scissors
            if (player2Choice.equals(getResources().getString(R.string.rock))) {
                return ARG_PLAYER_TWO;
            }
            else if (player2Choice.equals(getResources().getString(R.string.paper))) {
                return ARG_PLAYER_ONE;
            }
            else {
                return "tie";
            }
        }
    }


    private void displayWinner(String winner){

        //do a prompt about the winner
        new AlertDialog.Builder(getActivity())
                .setCancelable(true)
                .setTitle("Winner is:")
                .setMessage(winner)
                .setPositiveButton("Replay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_fragment_container, PlayFragment.newInstance(null, null))
                                .addToBackStack(null)
                                .commit();
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        getActivity().finish();
                    }
                })
                .show();

    }


}
