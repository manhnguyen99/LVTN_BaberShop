package Common;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.lvtn_babershop.Model.MyToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import io.paperdb.Paper;

public class Common {
    public static String IS_LOGIN = "IsLogin";
    public static String LOGGED_KEY = "LOGGED_KEY";

    public static enum TOKEN_TYPE{
        CLIENT,
        BARBER,
        MANAGER
    }
    public static void updateToken(Context context, final String s){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            MyToken myToken = new MyToken();
            myToken.setToken(s);
            myToken.setTokenType(TOKEN_TYPE.CLIENT);
            myToken.setUserPhone(user.getPhoneNumber());

            FirebaseFirestore.getInstance()
                    .collection("Tokens")
                    .document(user.getPhoneNumber())
                    .set(myToken)
                    .addOnCompleteListener(task -> {

                    });
        }
        else
        {
            Paper.init(context);
            String localUser = Paper.book().read(Common.LOGGED_KEY);
            if(localUser != null)
            {
                if(!TextUtils.isEmpty(localUser)){
                    MyToken myToken = new MyToken();
                    myToken.setToken(s);
                    myToken.setTokenType(TOKEN_TYPE.CLIENT);
                    myToken.setUserPhone(localUser);

                    FirebaseFirestore.getInstance()
                            .collection("Tokens")
                            .document(localUser)
                            .set(myToken)
                            .addOnCompleteListener(task -> {
                            });
                }
            }
        }
    }
}
