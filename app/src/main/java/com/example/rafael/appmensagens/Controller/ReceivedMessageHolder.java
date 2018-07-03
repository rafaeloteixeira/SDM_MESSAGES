package com.example.rafael.appmensagens.Controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rafael.appmensagens.Model.Mensagem;
import com.example.rafael.appmensagens.R;

/**
 * Created by rafael on 03/07/2018.
 */

public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText, nameText;
    ImageView profileImage;

    ReceivedMessageHolder(View itemView) {
        super(itemView);
        messageText = (TextView) itemView.findViewById(R.id.text_message_body);
        timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        nameText = (TextView) itemView.findViewById(R.id.text_message_name);
        profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
    }

    void bind(Mensagem message) {
        messageText.setText(message.getCorpo());

        // Format the stored timestamp into a readable String using method.
        //timeText.setText(Utils.formatDateTime(message.getCreatedAt()));
        nameText.setText(message.getOrigem().getApelido());

        // Insert the profile image from the URL into the ImageView.
        //Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
    }
}