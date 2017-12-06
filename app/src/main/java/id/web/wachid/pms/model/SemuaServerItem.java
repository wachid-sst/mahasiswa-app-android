package id.web.wachid.pms.model;

import com.google.gson.annotations.SerializedName;

public class SemuaServerItem {

	@SerializedName("nama_dosen")
	private String namaDosen;

	@SerializedName("id")
	private String id;

	@SerializedName("matkul")
	private String matkul;

	public void setNamaDosen(String namaDosen){
		this.namaDosen = namaDosen;
	}

	public String getNamaServer(){
		return namaDosen;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setMatkul(String matkul){
		this.matkul = matkul;
	}

	public String getIpServer(){
		return matkul;
	}

	@Override
 	public String toString(){
		return 
			"SemuaServerItem{" +
			"nama_dosen = '" + namaDosen + '\'' + 
			",id = '" + id + '\'' + 
			",matkul = '" + matkul + '\'' + 
			"}";
		}
}