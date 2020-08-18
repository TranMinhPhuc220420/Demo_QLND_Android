package com.example.quanlynhansu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.quanlynhansu.adapter.AdapterListView;
import com.example.quanlynhansu.database_layer.MyDatabase;
import com.example.quanlynhansu.model.NhanSu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  private ArrayList<NhanSu> listNhanSu = new ArrayList<>();
  private ListView lv;
  private EditText edt_fullname, edt_interests;
  private RadioGroup rdbtngr;
  private CheckBox ck_readbook, ck_watchmovie;
  private AdapterListView arrayAdapter;
  private MyDatabase DAO;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    DAO = new MyDatabase(this);

    edt_fullname = findViewById(R.id.edt_fullname);
    edt_interests = findViewById(R.id.edt_interests);
    rdbtngr = findViewById(R.id.rdbtngr);
    ck_readbook = findViewById(R.id.ck_readbook);
    ck_watchmovie = findViewById(R.id.ck_watchmovie);
    Button btn_add = findViewById(R.id.btn_add);
    Button btn_exit = findViewById(R.id.btn_exit);
    lv = findViewById(R.id.lv_nhansu);

    // Get database
    DAO.getMembers(this.listNhanSu);

    arrayAdapter = new AdapterListView(this, R.layout.activity_item_list_layout, this.listNhanSu);
    lv.setAdapter(arrayAdapter);


    // Add new person
    btn_add.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String fullName = edt_fullname.getText().toString();

        String degrees = "";
        int selectedId = rdbtngr.getCheckedRadioButtonId();
        RadioButton rbtn_selected = findViewById(selectedId);
        try {
          switch (rbtn_selected.getText().toString()) {
            case "TC":
              degrees = "Trung cấp";
              break;
            case "CĐ":
              degrees = "Cao Đẳng";
              break;
            case "ĐH":
              degrees = "Đại Học";
              break;
          }
        } catch (Exception ex) {
          degrees = "Không bằng cấp";
        }

        String hoppies = "";
        if (ck_readbook.isChecked()) {
          hoppies += ck_readbook.getText().toString();
        }
        if (ck_watchmovie.isChecked()) {
          if (!hoppies.equals("")) hoppies += ", ";
          hoppies += ck_watchmovie.getText().toString();
        }
        hoppies += !hoppies.equals("") ? ", " + edt_interests.getText().toString() : edt_interests.getText().toString();
        // Do add
        if (!fullName.isEmpty()) {
          addNewPerson(fullName, degrees, hoppies);
        } else {
          openDialog("Lỗi thêm", "KhÔng thấy tên nhân sự mới");
        }
      }
    });
    // finish exitApplication
    btn_exit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        exitApplication();
      }
    });
  }


  private void addNewPerson(String fullname, String degrees, String hoppies) {
    this.listNhanSu.add(new NhanSu(fullname, degrees, hoppies));
    this.lv.setAdapter(arrayAdapter);

    // Clear form
    this.resetForm();
  }

  private void resetForm() {
    edt_fullname.setText("");
    edt_fullname.requestFocus();
    edt_interests.setText("");
    ck_readbook.setChecked(false);
    ck_watchmovie.setChecked(false);
    rdbtngr.clearCheck();
  }

  private void exitApplication() {
    finish();
  }

  public void openDialog(String title, String message) {
    new AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("ok", (new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                edt_fullname.requestFocus();
              }
            })).show();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_layout, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int meuID = item.getItemId();

    switch (meuID) {
      case R.id.menuItem_delete:
        this.removeItem();
        this.lv.setAdapter(arrayAdapter);
        break;

      case R.id.menuItem_update:
        break;

      case R.id.menuItem_save:
        saveListMember();
        break;

      default:
        break;
    }

    return super.onOptionsItemSelected(item);
  }

  private void removeItem(){
    for (int i = 0; i < listNhanSu.size(); i++){
      if (listNhanSu.get(i).isChecked()){
        DAO.removeMembers(listNhanSu.get(i));
        listNhanSu.remove(i);
        i--;
      }
    }
  }

  private void saveListMember(){
    DAO.saveMembers(listNhanSu);
  }
}
