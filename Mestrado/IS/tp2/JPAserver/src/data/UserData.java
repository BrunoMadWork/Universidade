package data;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Logins")
public class UserData implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "LoginId")
	private Long id;
	private String login_name;
	@Column(name = "password")
	private String password;
	@Column(name = "name")
	private String name;
	@Column(name = "emails")
	private String email;
	public UserData() {

	}


	public UserData(String login_name, String password, String name,
			String email) {
		super();
		this.login_name = login_name;
		this.password = password;
		this.name = name;
		this.email = email;
	}
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}
	@Override
	public boolean equals(Object object)
	{
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof UserData))
		{
			return false;
		}
		UserData other = (UserData) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}
	@Override
	public String toString()
	{
		return "TOSTRINGGGGGGGASDASDSD";
	}


}
