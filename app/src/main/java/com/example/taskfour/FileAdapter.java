package com.example.taskfour;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private Context context;
    private List<FileModel> fileList;

    public FileAdapter(Context context, List<FileModel> fileList) {
        this.context = context;
        this.fileList = fileList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.file_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FileModel file = fileList.get(position);
        holder.tvFileName.setText(file.getFileName());
        holder.tvUploadTime.setText("Uploaded on: " + file.getUploadTime());

        holder.btnDownload.setOnClickListener(v -> {
            // Open file URL in a browser
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(file.getFileUrl()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFileName, tvUploadTime;
        Button btnDownload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFileName = itemView.findViewById(R.id.tvFileName);
            tvUploadTime = itemView.findViewById(R.id.tvUploadTime);
            btnDownload = itemView.findViewById(R.id.btnDownload);
        }
    }
}

