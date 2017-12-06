package id.web.wachid.pms.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseServer {

	@SerializedName("semuamatkul")
	private List<SemuaServerItem> semuamatkul;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public void setSemuamatkul(List<SemuaServerItem> semuamatkul){
		this.semuamatkul = semuamatkul;
	}

	public List<SemuaServerItem> getSemuamatkul(){
		return semuamatkul;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ResponseServer{" +
			"semuamatkul = '" + semuamatkul + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}