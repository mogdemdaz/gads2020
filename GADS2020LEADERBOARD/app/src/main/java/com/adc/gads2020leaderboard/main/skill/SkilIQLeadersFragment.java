package com.adc.gads2020leaderboard.main.skill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adc.gads2020leaderboard.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class SkilIQLeadersFragment extends Fragment {
    final String url = "https://gadsapi.herokuapp.com/api/skilliq";
    List<SkillIQLeadersModel> mSkillIQLeadersList;
    SkillIQLeadersRecyclerAdapter mSkillIQLeadersRecyclerAdapter;
    RequestQueue requestQueue;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.skill_iq_leaders, container, false);
        initializeDisplayContent(root);
        return root;
    }

    private void initializeDisplayContent(View root) {
        final RecyclerView recyclerView = root.findViewById(R.id.skillIQLeadersRecyclerView);
        mSkillIQLeadersList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(root.getContext());//This will take care of
        //background newtwork activities

        final LinearLayoutManager notesLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(notesLayoutManager);

        getData(root);

        mSkillIQLeadersRecyclerAdapter = new SkillIQLeadersRecyclerAdapter(root.getContext(), mSkillIQLeadersList);
        recyclerView.setAdapter(mSkillIQLeadersRecyclerAdapter);
    }

    public void getData(final View root) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i = 0; i < response.length(); i++)
                            {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                int score = jsonObject.getInt("score");
                                String country = jsonObject.getString("country");
                                String badgeUrl = jsonObject.getString("badgeUrl");

                                mSkillIQLeadersList.add(new SkillIQLeadersModel(name, score, country, badgeUrl));
                            }
                            mSkillIQLeadersRecyclerAdapter.notifyDataSetChanged();//To prevent app from crashing when updating
                            //UI through background Thread
                        }
                        catch (Exception w)
                        {
                            Snackbar.make(root, Objects.requireNonNull(w.getMessage()), Snackbar.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(root, Objects.requireNonNull(error.getMessage()), Snackbar.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
}