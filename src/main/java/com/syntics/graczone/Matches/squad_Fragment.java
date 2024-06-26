package com.syntics.graczone.Matches;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.syntics.graczone.R;

public class squad_Fragment extends Fragment {


    private FirestoreRecyclerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_squad_, container, false);


        RecyclerView mFirestoreList = view.findViewById(R.id.firestore_list);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("SQUAD");
        FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                .setQuery(query, ProductsModel.class).build();

        adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_squad, parent, false);
                return new ProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {
                holder.time.setText(model.getTime());
                holder.entry_fee.setText(model.getEntry_fee());
                holder.rs_per_kill.setText(model.getRs_per_kill());
                holder.teamup.setText(model.getTeamup());
                holder.rank1.setText(model.getRank1());
                holder.rank2.setText(model.getRank2());
                holder.rank3.setText(model.getRank3());
                holder.date.setText(model.getDate());
                holder.map.setText(model.getMap());
                holder.count.setText((model.getCount() + "/100"));
                holder.match.setText(model.getMatch());
                holder.linearProgressIndicator.setProgress(Integer.parseInt(model.getCount()));


//                if (Integer.parseInt(model.getCount()) == 100) {
////                    holder.itemView.setEnabled(false);
//                    holder.count.setText("Full");
//                }


                holder.itemView.setOnClickListener(v -> {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Intent intent = new Intent(getActivity(), joining.class);

                    intent.putExtra("entry_fee", model.getEntry_fee());
                    intent.putExtra("rs_per_kill", model.getRs_per_kill());
                    intent.putExtra("rank1", model.getRank1());
                    intent.putExtra("rank2", model.getRank2());
                    intent.putExtra("rank3", model.getRank3());
                    intent.putExtra("teamup", model.getTeamup());
                    intent.putExtra("map", model.getMap());
                    intent.putExtra("time", model.getTime());
                    intent.putExtra("date", model.getDate());
                    intent.putExtra("match", model.getMatch());
                    intent.putExtra("count", model.getCount());

                    startActivity(intent);
                });


            }
        };


        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getContext()));
        mFirestoreList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    static class ProductsViewHolder extends RecyclerView.ViewHolder {
        private final TextView time;
        private final TextView entry_fee;
        private final TextView rs_per_kill;
        private final TextView teamup;
        private final TextView rank1;
        private final TextView rank2;
        private final TextView rank3;
        private final TextView date;
        private final TextView map;
        private final TextView match;
        private final TextView count;
        private final LinearProgressIndicator linearProgressIndicator;

        public ProductsViewHolder(View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time);
            entry_fee = itemView.findViewById(R.id.entryFee);
            rs_per_kill = itemView.findViewById(R.id.rs_per_kill);
            teamup = itemView.findViewById(R.id.teamup);
            rank1 = itemView.findViewById(R.id.rank1);
            rank2 = itemView.findViewById(R.id.rank2);
            rank3 = itemView.findViewById(R.id.rank3);
            date = itemView.findViewById(R.id.date);
            map = itemView.findViewById(R.id.map);
            match = itemView.findViewById(R.id.match_number);
            count = itemView.findViewById(R.id.countTextView);
            linearProgressIndicator = itemView.findViewById(R.id.filling_progressbar);


        }
    }


}