package com.sromku.simple.fb.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.json.JSONArray;
import org.json.JSONObject;

import com.facebook.model.GraphLocation;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.Properties;

/**
 * The facebook user
 * 
 * @author sromku
 * @see https://developers.facebook.com/docs/reference/api/user/
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileFB implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4585358426575388531L;

	private static final String ID = "id";
	private static final String NAME = "name";

	private String id;

	private String name;

	private String firstName;

	private String middleName;

	private String lastName;

	private String gender;

	private String locale;

	@JsonIgnore
	private transient List<Language> languages;

	private String link;

	private String username;

	private String ageRange;

	private String thirdPartyId;

	private boolean installed;

	private int timezone;

	private String updatedTime;

	private boolean verified;

	private String bio;

	private String birthday;

	private String cover;

	private String currency;

	@JsonIgnore
	private transient List<Education> education;

	private String email;

	private String hometown;

	private Location location;

	private String political;

	@JsonIgnore
	private transient List<String> favoriteAthletes;

	@JsonIgnore
	private transient List<String> favoriteTeams;

	private String picture;

	private String quotes;

	private String relationshipStatus;

	private String religion;

	private String website;

	@JsonIgnore
	private transient List<Work> work;

	@JsonIgnore
	private transient GraphUser mGraphUser;

	public ProfileFB() {
		mGraphUser = null;
	}

	public ProfileFB(GraphUser profile) {
		this.mGraphUser = profile;
	}

	/**
	 * Create new profile based on {@link GraphUser} instance.
	 * 
	 * @param graphUser
	 *            The {@link GraphUser} instance
	 * @return
	 */
	public static ProfileFB create(GraphUser user) {
		return new ProfileFB(user);
	}

	/**
	 * Returns the ID of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the ID of the user
	 */
	public String getId() {
		if (getmGraphUser() != null)
			id = getmGraphUser().getId();
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the name of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the name of the user
	 */
	public String getName() {
		if (getmGraphUser() != null)
			name = getmGraphUser().getName();
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the first name of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the first name of the user
	 */
	public String getFirstName() {
		if (getmGraphUser() != null)
			firstName = getmGraphUser().getFirstName();
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the middle name of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the middle name of the user
	 */
	public String getMiddleName() {
		if (getmGraphUser() != null)
			middleName = getmGraphUser().getMiddleName();
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * Returns the last name of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the last name of the user
	 */
	public String getLastName() {
		if (getmGraphUser() != null)
			lastName = getmGraphUser().getLastName();
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the gender of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the gender of the user
	 */
	public String getGender() {
		if (getmGraphUser() != null)
			gender = String.valueOf(getmGraphUser().getProperty(
					Properties.GENDER));
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Return the ISO language code and ISO country code of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the ISO language code and ISO country code of the user
	 */
	public String getLocale() {
		if (getmGraphUser() != null)
			locale = String.valueOf(getmGraphUser().getProperty(
					Properties.LOCALE));
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * Return the languages of the user.<br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_LIKES}
	 * 
	 * @return the languages of the user
	 */
	@JsonIgnore
	public List<Language> getLanguages() {
		if (getmGraphUser() != null) {
			languages = new ArrayList<Language>();

			JSONArray jsonArray = (JSONArray) getmGraphUser().getProperty(
					Properties.LANGUAGE);
			if (jsonArray != null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.optJSONObject(i);
					int id = jsonObject.optInt(ID);
					String name = jsonObject.optString(NAME);

					Language language = new Language();
					language.setId(id);
					language.setName(name);
					languages.add(language);
				}
			}
		}

		return languages;
	}

	/**
	 * Returns the Facebook URL of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the Facebook URL of the user
	 */
	public String getLink() {
		if (getmGraphUser() != null)
			link = getmGraphUser().getLink();
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Returns the Facebook username of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the Facebook username of the user
	 */
	public String getUsername() {
		if (getmGraphUser() != null)
			username = getmGraphUser().getUsername();
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * The user's age range. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the user's age range
	 */
	public String getAgeRange() {
		if (getmGraphUser() != null) {
			JSONObject jsonObject = (JSONObject) getmGraphUser().getProperty(
					Properties.AGE_RANGE);
			if (jsonObject != null) {
				String min = jsonObject.optString("min");
				String max = jsonObject.optString("max");
				ageRange = min + max;
			}
		}
		return ageRange;
	}

	/**
	 * An anonymous, but unique identifier for the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return the an anonymous, but unique identifier for the user
	 */
	public String getThirdPartyId() {
		if (getmGraphUser() != null) {
			Object property = getmGraphUser().getProperty(
					Properties.THIRD_PARTY_ID);
			thirdPartyId = String.valueOf(property);
		}
		return thirdPartyId;
	}

	/**
	 * Specifies whether the user has installed the application associated with
	 * the app access token that is used to make the request. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return <code>True</code> if installed, otherwise <code>False</code>
	 */
	public boolean getInstalled() {
		if (getmGraphUser() != null) {
			try {
				installed = (Boolean) getmGraphUser().asMap().get(
						Properties.INSTALLED);
			} catch (Exception ignored) {
			}
		}
		return installed;
	}

	/**
	 * Return the timezone of the user.<br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * <br>
	 * <br>
	 * <b>Note:</b> <br>
	 * Avaliable only for my profile
	 * 
	 * @return the timezone of the user
	 */
	public int getTimeZone() {
		if (getmGraphUser() != null) {
			timezone = Integer.valueOf(getmGraphUser().getProperty(
					Properties.TIMEZONE).toString());
		}
		return timezone;
	}

	/**
	 * The last time the user's profile was updated; changes to the languages,
	 * link, timezone, verified, interested_in, favorite_athletes,
	 * favorite_teams, and video_upload_limits are not not reflected in this
	 * value.<br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * <br>
	 * <br>
	 * 
	 * @return string containing an ISO-8601 datetime
	 */
	public String getUpdatedTime() {
		if (getmGraphUser() != null) {
			updatedTime = String.valueOf(getmGraphUser().getProperty(
					Properties.UPDATED_TIME));
		}
		return updatedTime;
	}

	/**
	 * The user's account verification status.<br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * <br>
	 * <br>
	 * <b>Note:</b> <br>
	 * A user is considered verified if she takes any of the following actions:
	 * <li>Registers for mobile</li> <li>Confirms her account via SMS</li> <li>
	 * Enters a valid credit card</li> <br>
	 * <br>
	 * 
	 * @return The user's account verification status
	 */
	public Boolean getVerified() {
		if (getmGraphUser() != null) {
			try {
				verified = (Boolean) getmGraphUser().asMap().get(
						Properties.INSTALLED);
			} catch (Exception ignored) {
			}
		}
		return verified;
	}

	/**
	 * Return the biography of the user.<br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_ABOUT_ME}<br>
	 * {@link Permissions#FRIENDS_ABOUT_ME}
	 * 
	 * @return the biography of the user
	 */
	public String getBio() {
		if (getmGraphUser() != null) {
			bio = String.valueOf(getmGraphUser().getProperty(Properties.BIO));
		}
		return bio;
	}

	/**
	 * Returns the birthday of the user. <b>MM/DD/YYYY</b> format <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_BIRTHDAY} <br>
	 * {@link Permissions#FRIENDS_BIRTHDAY}
	 * 
	 * @return the birthday of the user
	 */
	public String getBirthday() {
		if (getmGraphUser() != null) {
			birthday = getmGraphUser().getBirthday();
		}
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * The user's cover photo <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return The user's cover photo
	 */
	public String getCover() {
		if (getmGraphUser() != null) {
			JSONObject jsonObject = (JSONObject) getmGraphUser().getProperty(
					Properties.COVER);
			if (jsonObject != null) {
				cover = jsonObject.optString("source");
			}
		}
		return cover;
	}

	/**
	 * The user's currency settings <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return The user's currency settings
	 */
	public String getCurrency() {
		if (getmGraphUser() != null) {
			JSONObject jsonObject = (JSONObject) getmGraphUser().getProperty(
					Properties.CURRENCY);
			if (jsonObject != null) {
				currency = jsonObject.optString("user_currency");
			}
		}
		return currency;
	}

	/**
	 * The user's education history <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_EDUCATION_HISTORY}<br>
	 * {@link Permissions#FRIENDS_EDUCATION_HISTORY}
	 * 
	 * @return The user's education history
	 */
	@JsonIgnore
	public List<Education> getEducation() {
		if (getmGraphUser() != null) {
			education = new ArrayList<Education>();
			GraphObjectList<GraphObject> graphObjectList = getmGraphUser()
					.getPropertyAsList(Properties.EDUCATION, GraphObject.class);
			for (GraphObject graphObject : graphObjectList) {
				Education ed = Education.create(graphObject);
				education.add(ed);
			}
		}
		return education;
	}

	/**
	 * Return the email of the user.<br>
	 * <br>
	 * <b> Permissions:</b> <br>
	 * {@link Permissions#EMAIL}
	 * 
	 * @return the email of the user
	 */
	public String getEmail() {
		if (getmGraphUser() != null) {
			email = String.valueOf(getmGraphUser()
					.getProperty(Properties.EMAIL));
		}
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * The user's hometown <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_HOMETOWN}<br>
	 * {@link Permissions#FRIENDS_HOMETOWN}
	 * 
	 * @return The user's hometown
	 */
	public String getHometown() {
		if (getmGraphUser() != null) {
			hometown = String.valueOf(getmGraphUser().getProperty(
					Properties.HOMETOWN));
		}
		return hometown;
	}

	/**
	 * Returns the current city of the user. <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_LOCATION}<br>
	 * {@link Permissions#FRIENDS_LOCATION}
	 * 
	 * @return the current city of the user
	 */
	public Location getLocation() {
		if (getmGraphUser() != null) {
			GraphLocation graphLocation = getmGraphUser().getLocation();
			if (graphLocation != null) {
				location = Location.create(graphLocation);
			}
		}
		return location;
	}

	/**
	 * The user's political view <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_RELIGION_POLITICS}<br>
	 * {@link Permissions#FRIENDS_RELIGION_POLITICS}
	 * 
	 * @return The user's political view
	 */
	public String getPolitical() {
		if (getmGraphUser() != null) {
			political = String.valueOf(getmGraphUser().getProperty(
					Properties.POLITICAL));
		}
		return political;
	}

	/**
	 * The user's favorite athletes <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_LIKES}<br>
	 * {@link Permissions#FRIENDS_LIKES}
	 * 
	 * @return The user's favorite athletes
	 */
	@JsonIgnore
	public List<String> getFavoriteAthletes() {
		if (getmGraphUser() != null) {
			favoriteAthletes = new ArrayList<String>();
			JSONArray jsonArray = (JSONArray) getmGraphUser().getProperty(
					Properties.FAVORITE_ATHLETES);
			if (jsonArray != null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.optJSONObject(i);
					if (jsonObject != null) {
						String name = jsonObject.optString(NAME);
						favoriteAthletes.add(name);
					}
				}
			}
		}
		return favoriteAthletes;
	}

	/**
	 * The user's favorite teams <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_LIKES}<br>
	 * {@link Permissions#FRIENDS_LIKES}
	 * 
	 * @return The user's favorite teams
	 */
	@JsonIgnore
	public List<String> getFavoriteTeams() {
		if (getmGraphUser() != null) {
			favoriteTeams = new ArrayList<String>();
			JSONArray jsonArray = (JSONArray) getmGraphUser().getProperty(
					Properties.FAVORITE_TEAMS);
			if (jsonArray != null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.optJSONObject(i);
					if (jsonObject != null) {
						String name = jsonObject.optString(NAME);
						favoriteTeams.add(name);
					}
				}
			}
		}
		return favoriteTeams;
	}

	/**
	 * The user's profile pic <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#BASIC_INFO}
	 * 
	 * @return The user's profile pic
	 */
	public String getPicture() {
		if (getmGraphUser() != null) {
			JSONObject result = (JSONObject) getmGraphUser().getProperty(
					Properties.PICTURE);
			if (result != null) {
				JSONObject data = result.optJSONObject("data");
				if (data != null)
					picture = data.optString("url");
			}
		}
		return picture;
	}

	/**
	 * The user's favorite quotes <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_ABOUT_ME}<br>
	 * {@link Permissions#FRIENDS_ABOUT_ME}
	 * 
	 * @return The user's favorite quotes
	 */
	public String getQuotes() {
		if (getmGraphUser() != null) {
			quotes = String.valueOf(getmGraphUser().getProperty(
					Properties.QUOTES));
		}
		return quotes;
	}

	/**
	 * The user's relationship status: <br>
	 * <li>Single</li> <li>In a relationship</li> <li>Engaged</li> <li>Married</li>
	 * <li>It's complicated</li> <li>In an open relationship</li> <li>Widowed</li>
	 * <li>Separated</li> <li>Divorced</li> <li>In a civil union</li> <li>In a
	 * domestic partnership</li> <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_RELATIONSHIPS}<br>
	 * {@link Permissions#FRIENDS_RELATIONSHIPS}
	 * 
	 * @return The user's relationship status
	 */
	public String getRelationshipStatus() {
		if (getmGraphUser() != null) {
			relationshipStatus = String.valueOf(getmGraphUser().getProperty(
					Properties.RELATIONSHIP_STATUS));
		}
		return relationshipStatus;
	}

	public void setRelationshipStatus(String relationshipStatus) {
		this.relationshipStatus = relationshipStatus;
	}

	/**
	 * The user's religion <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_RELIGION_POLITICS}<br>
	 * {@link Permissions#FRIENDS_RELIGION_POLITICS}
	 * 
	 * @return The user's religion
	 */
	public String getReligion() {
		if (getmGraphUser() != null) {
			religion = String.valueOf(getmGraphUser().getProperty(
					Properties.RELIGION));
		}
		return religion;
	}

	/**
	 * The URL of the user's personal website <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_WEBSITE}<br>
	 * {@link Permissions#FRIENDS_WEBSITE}
	 * 
	 * @return The URL of the user's personal website
	 */
	public String getWebsite() {
		if (getmGraphUser() != null) {
			website = String.valueOf(getmGraphUser().getProperty(
					Properties.WEBSITE));
		}
		return website;
	}

	/**
	 * The user's work history <br>
	 * <br>
	 * <b> Permissions:</b><br>
	 * {@link Permissions#USER_WORK_HISTORY}<br>
	 * {@link Permissions#FRIENDS_WORK_HISTORY}
	 * 
	 * @return The user's work history
	 */
	@JsonIgnore
	public List<Work> getWork() {
		if (getmGraphUser() != null) {
			work = new ArrayList<Work>();
			GraphObjectList<GraphObject> graphObjectList = getmGraphUser()
					.getPropertyAsList(Properties.WORK, GraphObject.class);
			for (GraphObject graphObject : graphObjectList) {
				Work wk = Work.create(graphObject);
				work.add(wk);
			}
		}
		return work;
	}

	@JsonIgnore
	public GraphUser getmGraphUser() {
		return mGraphUser;
	}

}
