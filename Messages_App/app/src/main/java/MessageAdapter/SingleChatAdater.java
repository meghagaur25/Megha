package MessageAdapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messages_app.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import Room.Entity.SingleChat;
import ViewModels.SingleChatModel;

public class SingleChatAdater extends RecyclerView.Adapter<SingleChatAdater.IndividualChatViewHolder> {
        private List<SingleChatModel> singleChatModels;
        private LayoutInflater layoutInflater;
        private Context mContext;
        private List<SingleChat> singleChats;

        public SingleChatAdater(Context context){
            layoutInflater = LayoutInflater.from(context);
            mContext = context;
        }

        @NonNull
        @Override
        public IndividualChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.activity_chat,parent,false);
            IndividualChatViewHolder holder = new IndividualChatViewHolder(view);
            return holder;
        }

        @Override
        public int getItemCount() {
            if(singleChats!=null) {
                return singleChats.size();
            }else{
                return 0;
            }
        }

        public void setChatNotes(List<SingleChat> singleChats){
            this.singleChats = singleChats;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(@NonNull IndividualChatViewHolder holder, int position) {
            SingleChat current = singleChats.get(position);
            holder.setData(current,position);
        }

        class IndividualChatViewHolder extends  RecyclerView.ViewHolder{

            private TextView singleChatTextView, singleChatTSTextView;
            private LinearLayout layout;
            private CardView card;
            private int position;
            private SingleChat singleChat;

            public IndividualChatViewHolder(@NonNull View itemView) {
                super(itemView);
                singleChatTextView = (TextView)itemView.findViewById(R.id.singleChatTextView);
                singleChatTSTextView = (TextView)itemView.findViewById(R.id.singleChatTSTextView);
                layout = (LinearLayout)itemView.findViewById(R.id.singleChatLayoutWrapper);
                card = (CardView)itemView.findViewById(R.id.icCard);
            }

            public void setData(SingleChat current, int position) {
                this.singleChatTextView.setText(current.getSmsBody());

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
                    formatter = DateFormat.getTimeInstance(DateFormat.SHORT);
                    dateText = dateText+" "+formatter.format(date);
                }
                this.singleChatTSTextView.setText(dateText);

                this.position = position;
                this.singleChat = current;

                String smsType = current.getSmsType();
                if(smsType.equals("sent")){
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(50,0,0,0);
                    layout.setLayoutParams(params);
                    layout.setGravity(Gravity.RIGHT);
                    card.setCardBackgroundColor(mContext.getResources().getColor(R.color.senderChatCardColor));
                }else{
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0,0,50,0);
                    layout.setLayoutParams(params);
                    layout.setGravity(Gravity.LEFT);
                    card.setCardBackgroundColor(mContext.getResources().getColor(R.color.receiverChatCardColor));

                }
            }
        }

    }
