package server.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;

import server.database.service.HouseService;

public class User extends AbstractModel {
	private Integer id;
	private String login;
	private String pwd;
	private String mail;
	private String avatar;
	private House house;
	private HouseService service;

	public User() {
		super();
		this.service = new HouseService();
		this.id = 0;
		this.login = "";
		this.pwd = "";
		this.mail = "";
		this.avatar = "";
		this.house = this.service.findByTitle("empty house");
	}

	public User(Integer id, String login, String mdp, String mail, String avatar) {
		super();
		this.service = new HouseService();
		this.id = id;
		this.login = login;
		this.pwd = mdp;
		this.mail = mail;
		this.avatar = avatar;
		this.house = this.service.findByTitle("empty house");
	}

	public Integer getId() {
		return this.id;
	}

	public User setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getLogin() {
		return this.login;
	}

	public User setLogin(String login) {
		this.login = login;
		return this;
	}

	public String getPwd() {
		return this.pwd;
	}

	public User setPwd(String pwd) {
		this.pwd = pwd;
		this.cryptPassword();
		return this;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public void setAvatar(String image) {
		this.avatar = image;
	}

	public String getAvatar() {
		return avatar;
	}

	static public String crypt(String s) {
		String text = s;

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(text.getBytes());

		byte byteData[] = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();

	}

	public void cryptPassword() {

		this.pwd = crypt(this.pwd);

	}

	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof User
				&& ((User) o).getId() == this.getId()
				&& ((User) o).getLogin().equals(this.getLogin())
				&& ((User) o).getPwd().equals(this.getPwd());
	}

	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", this.id);
			jsonObject.put("login", this.login);
			jsonObject.put("house", this.house.toJson());
			jsonObject.put("avatar", this.avatar);
			jsonObject.put("mail", this.getMail());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

}
