package com.example.quanlynhansu.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlynhansu.R;
import com.example.quanlynhansu.model.NhanSu;

import java.util.ArrayList;
import java.util.List;

public class AdapterListView extends ArrayAdapter<NhanSu> {
  private Activity context;
  private int layoutID;
  private ArrayList<NhanSu> listMembers;

  public AdapterListView(Activity context, int resource, ArrayList<NhanSu> listMembers) {
    super(context, resource, listMembers);
    this.context = context;
    this.layoutID = resource;
    this.listMembers = listMembers;
  }

  static class ViewHolder {
    ImageView imgMain;
    TextView txtfullname;
    TextView txtdegree;
    CheckBox ckbSelected;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    final ViewHolder viewHolder;

    if (convertView == null) {
      viewHolder = new ViewHolder();
      convertView = context.getLayoutInflater().inflate(layoutID, parent, false);
      viewHolder.imgMain = (ImageView) convertView.findViewById(R.id.imgMain);
      viewHolder.txtfullname = (TextView) convertView.findViewById(R.id.txtfullname);
      viewHolder.txtdegree = (TextView) convertView.findViewById(R.id.txtdegree);
      viewHolder.ckbSelected = (CheckBox) convertView.findViewById(R.id.ckb_selected);

      NhanSu nhansu = listMembers.get(position);
      viewHolder.txtfullname.setText(nhansu.getName());
      viewHolder.txtdegree.setText(nhansu.getDegree());
      viewHolder.imgMain.setImageResource(R.mipmap.caodang);

      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
      viewHolder.ckbSelected.setChecked(listMembers.get(position).isChecked());
    }

    viewHolder.ckbSelected.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (viewHolder.ckbSelected.isChecked()) {
          Log.d("test", "onClick: True");
          listMembers.get(position).setChecked(true);
        } else {
          listMembers.get(position).setChecked(false);
        }
      }
    });

    return convertView;
  }
}
