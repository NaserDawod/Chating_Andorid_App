package com.example.chating_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chating_app.Data.Message;
import com.example.chating_app.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>  {

    class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView content;
        private TextView time;
        private RelativeLayout layout;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            this.content = itemView.findViewById(R.id.messageItem);
            this.time = itemView.findViewById(R.id.messageTime);
            this.layout = itemView.findViewById(R.id.messageLayout);
        }
    }

    private LayoutInflater mInflter;
    private List<Message> messages;

    public void setMessages(List<Message> messages){
        this.messages = messages;
        notifyDataSetChanged();
    }

    public MessageAdapter(Context context) {
        mInflter = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflter.inflate(R.layout.message_send,parent,false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if (messages !=null){
            final Message message = messages.get(position);
            holder.content.setText(message.content);
            holder.time.setText(message.created.substring(11,16));
            if (message.sent) { // right
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.layout.getLayoutParams();
                params.setMargins(0, 0, 10, 0);
                params.addRule(RelativeLayout.ALIGN_PARENT_END);
                holder.layout.setLayoutParams(params);
                holder.layout.setBackgroundResource(R.drawable.send_background);

            } else { // left
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.layout.getLayoutParams();
                params.setMargins(10, 0, 0, 0);
                params.addRule(RelativeLayout.ALIGN_PARENT_START);
                holder.layout.setLayoutParams(params);
                holder.layout.setBackgroundResource(R.drawable.reciever_background);
            }
        }

    }

    @Override
    public int getItemCount() {
        if (messages !=null)
            return messages.size();
        else return 0;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
