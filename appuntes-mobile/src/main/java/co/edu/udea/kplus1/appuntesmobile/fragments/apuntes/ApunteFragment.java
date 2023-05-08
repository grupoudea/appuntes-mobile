package co.edu.udea.kplus1.appuntesmobile.fragments.apuntes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.R;

public class ApunteFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private ImageButton mCameraButton;
    private ImageButton mMicButton;
    private ImageButton mSendButton;
    private List<Apunte> mApuntesList = new ArrayList<>();
    private ApunteAdapter mApunteAdapter;

    public ApunteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_apunte, container, false);


        mRecyclerView = view.findViewById(R.id.cyclerViewApuntes);
        mEditText = view.findViewById(R.id.editText);
        mCameraButton = view.findViewById(R.id.cameraButton);
        mMicButton = view.findViewById(R.id.micButton);
        mSendButton = view.findViewById(R.id.sendButton);

        // Initialize RecyclerView and Adapter
        mApunteAdapter = new ApunteAdapter(mApuntesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mApunteAdapter);

        // Set click listeners for buttons
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Foto
            }
        });

        mMicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Audio
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Texto
                String texto = mEditText.getText().toString().trim();
                if (!texto.isEmpty()) {
                    Apunte apunte = new Apunte(texto, new Date());
                    mApuntesList.add(apunte);
                    mApunteAdapter.notifyDataSetChanged();
                    mEditText.setText("");
                }
            }
        });

        return view;
    }
}

