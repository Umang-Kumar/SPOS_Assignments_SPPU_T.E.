
public class Tables {
	int indx;
	String body;
	int mdtpIndx;
	public Tables(int indx, String body, int mdtpIndx) {
		super();
		this.indx = indx;
		this.body = body;
		this.mdtpIndx = mdtpIndx;
	}
	public Tables(int indx, String body) {
		super();
		this.indx = indx;
		this.body = body;
	}
	public int getIndx() {
		return indx;
	}
	public void setIndx(int indx) {
		this.indx = indx;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public int getMdtpIndx() {
		return mdtpIndx;
	}
	public void setMdtpIndx(int mdtpIndx) {
		this.mdtpIndx = mdtpIndx;
	}
	
	
}
