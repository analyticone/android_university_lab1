package com.codepath.bestsellerlistapp;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.bestsellerlistapp.models.BestSellerBook;
import com.codepath.bestsellerlistapp.networking.CallbackResponse;
import com.codepath.bestsellerlistapp.networking.NYTimesApiClient;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class BestSellerBooksFragment extends Fragment implements OnListFragmentInteractionListener{

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BestSellerBooksFragment() {
    }

    @SuppressWarnings("unused")
    public static BestSellerBooksFragment newInstance(int columnCount) {
        BestSellerBooksFragment fragment = new BestSellerBooksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_best_seller_books_list, container, false);
        ContentLoadingProgressBar progressBar = (ContentLoadingProgressBar) view.findViewById(R.id.progress);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);

        Context context = view.getContext();
        int spanCount = (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? 4 : 2;

        recyclerView.setLayoutManager(new GridLayoutManager(context, spanCount));
        updateAdapter(progressBar, recyclerView);
        return view;
    }


    private void updateAdapter(final ContentLoadingProgressBar progressBar, final RecyclerView recyclerView) {
        progressBar.show();
        NYTimesApiClient nyTimesApiClient = new NYTimesApiClient();
        nyTimesApiClient.getBestSellersList(new CallbackResponse<List<BestSellerBook>>() {
            @Override
            public void onSuccess(List<BestSellerBook> models) {
                progressBar.hide();
                recyclerView.setAdapter(new BestSellerBooksRecyclerViewAdapter(models, BestSellerBooksFragment.this, getContext()));
                Log.d("BestSellerBooksFragment", "response successful");
            }

            @Override
            public void onFailure(Throwable error) {
                progressBar.hide();
                Log.e("BestSellerBooksFragment", error.getMessage());
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(BestSellerBook item) {
        Toast.makeText(getContext(), "test", Toast.LENGTH_LONG).show();
    }
}
