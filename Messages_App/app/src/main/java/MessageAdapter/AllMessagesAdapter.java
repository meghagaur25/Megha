package MessageAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messages_app.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import Room.Entity.AllMessages;


 public class AllMessagesAdapter extends RecyclerView.Adapter<AllMessagesAdapter.AllChatSummaryViewHolder> {
        private LayoutInflater layoutInflater;
        private List<AllMessages> allMessagesList;
        private OnChatSummaryListener onChatSummaryListener;

        public AllMessagesAdapter(Context context, OnChatSummaryListener onChatSummaryListener){
            layoutInflater = LayoutInflater.from(context);
            this.onChatSummaryListener = onChatSummaryListener;
        }

        @NonNull
        @Override
        public AllChatSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.message_list,parent,false);
            AllChatSummaryViewHolder holder = new AllChatSummaryViewHolder(view, onChatSummaryListener);
            return holder;
        }

        @Override
        public int getItemCount() {
            if(allMessagesList!=null) {
                return allMessagesList.size();
            }else{
                return 0;
            }
        }

        public void setChatNotes(List<AllMessages> allMessagesList){
            this.allMessagesList = allMessagesList;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(@NonNull AllChatSummaryViewHolder holder, int position) {
            AllMessages current = allMessagesList.get(position);
            holder.setData(current,position);
        }

        public class AllChatSummaryViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

            private TextView contactName;
            private TextView lastMessage;
            private TextView timeStamp;
            private int position;
            private AllMessages allMessagesList;
            OnChatSummaryListener onChatSummaryListener;

            public AllChatSummaryViewHolder(@NonNull View itemView, OnChatSummaryListener onChatSummaryListener) {
                super(itemView);
                contactName = (TextView)itemView.findViewById(R.id.contactName);
                lastMessage = (TextView)itemView.findViewById(R.id.last_sms_body);
                timeStamp = (TextView)itemView.findViewById(R.id.timestamp);

                this.onChatSummaryListener = onChatSummaryListener;
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view){
                onChatSummaryListener.onChatSummaryClick(getAdapterPosition());
            }

            public void setData(AllMessages current, int position) {
                this.contactName.setText(current.getContactName());

                String lastMessageText = current.getLastMessage();
                if(lastMessageText.length() >= 50){
                    lastMessageText = lastMessageText.substring(0,49)+"...";
                }
                this.lastMessage.setText(lastMessageText);


                Date date = current.getSmsTimestamp();
                Date currentDate = new Date();
                DateFormat formatter;
                String dateText;
                formatter = DateFormat.getDateInstance(DateFormat.MEDIUM);
                if(formatter.format(date).equals(formatter.format(currentDate))){
                    formatter = DateFormat.getTimeInstance(DateFormat.SHORT);
                    dateText = formatter.format(date);
                }else{
                    formatter = DateFormat.getDateInstance(DateFormat.MEDIUM);
                    dateText = formatter.format(date);
                }
                this.timeStamp.setText(dateText);

                this.position = position;
                this.allMessagesList = current;
            }
        }


        public interface OnChatSummaryListener{
            void onChatSummaryClick(int position);
        }
}
