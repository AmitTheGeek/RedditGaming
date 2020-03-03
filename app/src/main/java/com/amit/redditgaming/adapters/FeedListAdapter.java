package com.amit.redditgaming.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.amit.redditgaming.databinding.FeedItemBinding;
import com.amit.redditgaming.databinding.NetworkItemBinding;
import com.amit.redditgaming.model.Children;
import com.amit.redditgaming.rest.RestApiFactory;
import com.amit.redditgaming.utils.NetworkState;
import com.bumptech.glide.Glide;

public class FeedListAdapter extends PagedListAdapter<Children, RecyclerView.ViewHolder> {

    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;

    private Context context;
    private NetworkState networkState;
    public FeedListAdapter(Context context) {
        super(Children.DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_PROGRESS) {
            NetworkItemBinding headerBinding = NetworkItemBinding.inflate(layoutInflater, parent, false);
            NetworkStateItemViewHolder viewHolder = new NetworkStateItemViewHolder(headerBinding);
            return viewHolder;

        } else {
            FeedItemBinding itemBinding = FeedItemBinding.inflate(layoutInflater, parent, false);
            ChildrenViewHolder viewHolder = new ChildrenViewHolder(itemBinding);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ChildrenViewHolder) {
            ((ChildrenViewHolder)holder).bindTo(getItem(position));
        } else {
            ((NetworkStateItemViewHolder) holder).bindView(networkState);
        }
    }


    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }


    public class ChildrenViewHolder extends RecyclerView.ViewHolder {

        private FeedItemBinding binding;
        public ChildrenViewHolder(FeedItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(final Children child) {
            SpannableString title = new SpannableString(child.getData().getTitle());
            binding.itemTitle.setText(title);

            SpannableString score = new SpannableString(child.getData().getScore() + "");
            binding.itemScore.setText(score);

            SpannableString subReddit = new SpannableString(child.getData().getSubreddit());
            binding.itemSubreddit.setText(subReddit);

            Glide.with(context).load(child.getData().getThumbnail()).into(binding.redditThumb);

            binding.rootView.setOnClickListener(v -> {
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                            RestApiFactory.getBaseUrl() + child.getData().getPermalink()));
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    FeedListAdapter.this.context.startActivity(myIntent);
                }catch(ActivityNotFoundException e){
                    Toast.makeText(context, "No app to open url", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        private NetworkItemBinding binding;
        public NetworkStateItemViewHolder(NetworkItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
                binding.errorMsg.setVisibility(View.VISIBLE);
                binding.errorMsg.setText(networkState.getMsg());
            } else {
                binding.errorMsg.setVisibility(View.GONE);
            }
        }
    }
}
