package com.example.welcome.travelmatev11.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.welcome.travelmatev11.Model.ImageInfo;
import com.example.welcome.travelmatev11.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ashna on 8/26/2016.
 */
public class CustomMemoryAdapter extends RecyclerView.Adapter<CustomMemoryAdapter.ImageViewHolder> {

        private ArrayList<ImageInfo> image;
        private Context context;
        int id;
        private final int THUMBSIZE = 96;
    public CustomMemoryAdapter(ArrayList<ImageInfo> image, Context context) {
            this.image = image;
            this.context=context;
        }

        public class ImageViewHolder extends RecyclerView.ViewHolder {

            private CardView cardView;
            private TextView caption;
            private TextView time;
            private ImageView icon_entry;

            public ImageViewHolder(View itemView) {
                super(itemView);
                cardView = (CardView) itemView.findViewById(R.id.cv);
                icon_entry=(ImageView) itemView.findViewById(R.id.icon_image);
                caption=(TextView)itemView.findViewById(R.id.caption);
                time=(TextView)itemView.findViewById(R.id.time);
            }
        }

        @Override
        public void onBindViewHolder(ImageViewHolder imageViewHolder, int i) {

            imageViewHolder.icon_entry.setImageBitmap(ThumbnailUtils
                    .extractThumbnail(BitmapFactory.decodeFile(image.get(i).getPath()),
                            THUMBSIZE, THUMBSIZE));
            imageViewHolder.caption.setText(image.get(i).getCaption());
            Long addDate = image.get(i).getDatetimeLong();
            Long currentDate = System.currentTimeMillis();
            long diff = addDate - currentDate;
            imageViewHolder.time.setText("Added "+ TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + " Days ago");


            final int pos=i;

           /* imageViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    *//*id = image.get(pos).getId();
                    Intent intentS=new Intent(context,Notes.class);
                    intentS.putExtra("idS",id);
                    context.startActivity(intentS);*//*


                    Toast.makeText(context,"clicked "+pos+" row", Toast.LENGTH_LONG).show();

                }
            });*/
        }

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);
            ImageViewHolder imageViewHolder = new ImageViewHolder(view);
            return imageViewHolder;
        }

        @Override
        public int getItemCount() {
            return image.size();
        }
    }



