package dongzhong.testforfloatingwindow;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button openfloat = (Button) findViewById(R.id.open_float);
        Button closefloat = (Button) findViewById(R.id.close_float);
        Button closeapp = (Button) findViewById(R.id.close_app);
        openfloat.setOnClickListener(this);
        closeapp.setOnClickListener(this);
        closefloat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_float) {
            stopService(new Intent(MainActivity.this, CameraService.class));
            Log.d("CameraService", " 关闭服务");
        } else if (v.getId() == R.id.close_app) {
            finish();
            Log.d("MainActivity", "关闭应用程序 ");
        } else if (v.getId() == R.id.open_float) {
            if (CameraService.isStarted) {
                return;
            }
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT);
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 2);
            } else {
                startService(new Intent(MainActivity.this, CameraService.class));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
            startService(new Intent(MainActivity.this, CameraService.class));
        }
    }

}

