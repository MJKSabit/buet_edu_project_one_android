package com.example.buet_edu_project_one_vmasum.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.buet_edu_project_one_vmasum.Adapters.ProblemsAdapter;
import com.example.buet_edu_project_one_vmasum.DataBase.RunTimeDB;
import com.example.buet_edu_project_one_vmasum.R;
import com.example.buet_edu_project_one_vmasum.Utils.JSONBuilder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "MainActivity:";
     public ProblemsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataFromFireStore();

        adapter = new ProblemsAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.problemsRecyclerViewId);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    public void getDataFromFireStore()
    {
        CollectionReference colRef = FirebaseFirestore.getInstance().collection("problem");
        colRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snapshot : list)
                        {
                            if(snapshot.exists())
                            {
                                RunTimeDB.getInstance().addNewProblem(snapshot.getData());
                                //  Log.w(TAG," title "+ problem.getTitle());
                             /*   try {
                                    JSONObject jsonObject = JSONBuilder.mapToJSON(snapshot.getData());
                                    RunTimeDB.getInstance().addNewProblem(jsonObject,snapshot.getData());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }*/
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}