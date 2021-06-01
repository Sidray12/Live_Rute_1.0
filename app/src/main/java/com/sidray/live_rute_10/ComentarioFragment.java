package com.sidray.live_rute_10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.sidray.live_rute_10.ui_usuario.home_usuario.HomeUsuarioFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComentarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComentarioFragment extends Fragment {

    private Button env_coment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ComentarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComentarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComentarioFragment newInstance(String param1, String param2) {
        ComentarioFragment fragment = new ComentarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_comentario, container, false);

        env_coment = root.findViewById(R.id.btn_comentario);

        env_coment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Comentario Enviado",Toast.LENGTH_SHORT).show();
                HomeUsuarioFragment homeUsuarioFragment = new HomeUsuarioFragment();
                FragmentTransaction fragmentTransaction = ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, homeUsuarioFragment);
                fragmentTransaction.commit();
                ((AppCompatActivity) getContext()).finish();



                //startActivity(new Intent(getContext(), HomeUsuarioActivity.class));


            }
        });

        return root;
    }
}