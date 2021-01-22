package com.example.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_image_download.*
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class ImageDownloadActivity : AppCompatActivity() {

    val handler = object: Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message){
            if(msg.what == 1){
                //데이터 가져오기
                val bitmap = msg.obj as Bitmap
                //이미지 출력하기
                img.setImageBitmap(bitmap)
            }

            if(msg.what == 2){
                //전달된 데이터 가져오기
                val filename = msg.obj as String
                //파일 경로 생성
                var path = Environment.getDataDirectory().absolutePath +
                        "/data/com.example.network/files/30.jpg" + filename
                //파일의 내용 출력
                img.setImageBitmap(BitmapFactory.decodeFile(path))
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_download)

        //이미지를 바로 출력하는 버튼의 클릭이벤트 처리
        btnImageDisplay.setOnClickListener{
            object:Thread(){
                override fun run() {
                    //스트림 생성
                    val inputStream = URL("https://pbs.twimg.com/profile_images/1257973237490712576/gEVnX19n_400x400.jpg").openStream()
                    //비트맵 가져오기
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream.close()
                    //핸들러에게 전송할 메시지 작성
                    val msg = Message()
                    msg.obj = bitmap
                    msg.what = 1
                    //핸들러에게 메시지 전송
                    handler.sendMessage(msg)
                }
            }.start()
        }

        //이미지 저장 버튼을 눌렀을 때 수행되는 코드
        btnImageSave.setOnClickListener{
            //파일의 경로를 생성
            var path = Environment.getDataDirectory().absolutePath +
                    "/data/com.example.network/files/30.jpg"
            //파일의 존재 여부 조사
            if(File(path).exists()){
                //있는 경우 바로 출력
                Toast.makeText(this,"파일이 존재 - 다운로드 받지 않음", Toast.LENGTH_LONG).show()
                Log.e("파일이 존재","다운로드 받지 않음")

                img.setImageBitmap(BitmapFactory.decodeFile(path))
            } else {
                Toast.makeText(this,"파일이 없음 - 다운로드 후 출력", Toast.LENGTH_LONG).show()
                Log.e("파일이 없음","다운로드 후 출력")

                //다운로드 받아서 핸들러를 이용해 출력
                object:Thread(){
                    override fun run(){
                        //스트림 생성
                        val url = URL("https://static.wikia.nocookie.net/stop-friendly-fire/images/5/54/Erian_Ruparte.jpg/revision/latest?cb=20200805225307")

                        val con = url.openConnection() as HttpURLConnection

                        val inputStream = con.inputStream

                        //다운로드 받은 내용을 저장할 바이트 배열 생성
                        Log.e("파일의 크기", con.contentLength.toString())
                        val raster = ByteArray(con.contentLength)
                        //파일 출력을 위한 스트림 생성
                        val fos = openFileOutput("30.jpg",0)
                        //읽어서 기록
                        while(true){
                            val len = inputStream.read(raster)
                            Log.e("len", len.toString())
                            if(len <= 0){
                                break
                            }
                            fos.write(raster, 0, len)
                        }

                        //정리 작업
                        inputStream.close()
                        fos.close()
                        con.disconnect()

                        //핸들러에게 파일 이름을 전송
                        val msg = Message()
                        msg.what = 2
                        msg.obj = "30.jpg"
                    }
                }.start()
            }
        }
    }
}