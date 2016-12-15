package com.example.anthony.gestionstock.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anthony.gestionstock.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAdminInfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAdminInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAdminInfo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View v;
    private EditText userName;
    private EditText password;
    private Button btn_conection;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private UserSession session;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAdminInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAdminInfo newInstance(String param1, String param2) {
        FragmentAdminInfo fragment = new FragmentAdminInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentAdminInfo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.admin_info, container, false);
        // Recupere la vue pour ce fragment
        initUI(v);

        return v;
    }

    private void initUI(View v) {
        userName = (EditText) v.findViewById(R.id.user_name);
        password = (EditText) v.findViewById(R.id.mot_de_passe);
        btn_conection = (Button) v.findViewById(R.id.btn_conection_admin);

        sharedPreferences = getActivity().getSharedPreferences("Admin", 0);
        editor = sharedPreferences.edit();

        btn_conection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = userName.getText().toString();
                String mdp = password.getText().toString();

                if (userName.getText().length() <= 0) {
                    Toast.makeText(getActivity(), "Entrez le nom", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().length() <= 0) {
                    Toast.makeText(getActivity(), "Entrez le Mot de passe", Toast.LENGTH_SHORT).show();
                }
                else {
                    editor.putString("Name", name);
                    editor.putString("MotDePasse", mdp);
                    editor.commit();
                }


                /*getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        // lancer un autre fragment
                        .replace(R.id.flContent, fragmentLogin, "string")
                        .addToBackStack(null)
                        .commit();
                Intent ob = new Intent(getContext(), Login.class);
                startActivity(ob);*/
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
