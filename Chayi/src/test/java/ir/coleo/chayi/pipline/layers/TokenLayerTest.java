package ir.coleo.chayi.pipline.layers;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ir.coleo.chayi.Chayi;
import ir.coleo.chayi.constats.Constants;
import ir.coleo.chayi.pipline.NetworkData;

import static ir.coleo.chayi.constats.Constants.TOKEN_DATA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TokenLayerTest {

    public TokenLayer tokenLayer;
    public static String TEMP_TOKEN = "asdasdasdasd";
    public static String RIGHT_TOKEN = "Token " + TEMP_TOKEN;

    @Mock
    public Context mockContext;
    @Mock
    public SharedPreferences mockSharedPreferences;

    @Before
    public void setup() {
        when(mockSharedPreferences.getString(TOKEN_DATA, Constants.NO_TOKEN)).thenReturn(TEMP_TOKEN);
        when(mockContext.getSharedPreferences(Constants.TOKEN_STORAGE, Context.MODE_PRIVATE)).thenReturn(mockSharedPreferences);
        Constants.context = mockContext;
        tokenLayer = new TokenLayer(null);
    }

    @Test
    public void testOne() {
        NetworkData data = new NetworkData(null, Chayi.class);
        data = tokenLayer.before(data);
        data = tokenLayer.work(data);
        data = tokenLayer.after(data);

    }


}