package com.devmix.snapshot.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sromku.simple.fb.entities.Education;
import com.sromku.simple.fb.entities.Language;
import com.sromku.simple.fb.entities.Location;
import com.sromku.simple.fb.entities.Work;

@DatabaseTable(tableName = "PERFIL")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = -4585358426575388531L;

	public static final String TABLE_NAME = "PERFIL";

	public static final String COLUMN_ID = "PRO_ID";
	@DatabaseField(columnName = COLUMN_ID, useGetSet = true, id = true)
	private long dbId;

	public static final String COLUMN_FB_ID = "PRO_FB_ID";
	@DatabaseField(columnName = COLUMN_FB_ID, useGetSet = true, canBeNull = false)
	private String id;

	public static final String COLUMN_NAME = "PRO_NAME";
	@DatabaseField(columnName = COLUMN_NAME, useGetSet = true)
	private String name;

	public static final String COLUMN_FIRST_NAME = "PRO_FIRST_NAME";
	@DatabaseField(columnName = COLUMN_FIRST_NAME, useGetSet = true)
	private String firstName;

	public static final String COLUMN_MIDDLE_NAME = "PRO_MIDDLE_NAME";
	@DatabaseField(columnName = COLUMN_MIDDLE_NAME, useGetSet = true)
	private String middleName;

	public static final String COLUMN_LAST_NAME = "PRO_LAST_NAME";
	@DatabaseField(columnName = COLUMN_LAST_NAME, useGetSet = true)
	private String lastName;

	public static final String COLUMN_GENDER = "PRO_GENDER";
	@DatabaseField(columnName = COLUMN_GENDER, useGetSet = true)
	private String gender;

	private String locale;

	@JsonIgnore
	private transient List<Language> languages;

	public static final String COLUMN_LINK = "PRO_LINK";
	@DatabaseField(columnName = COLUMN_LINK, useGetSet = true)
	private String link;

	public static final String COLUMN_USERNAME = "PRO_USERNAME";
	@DatabaseField(columnName = COLUMN_USERNAME, useGetSet = true)
	private String username;

	private String ageRange;

	private String thirdPartyId;

	private boolean installed;

	private int timezone;

	private String updatedTime;

	private boolean verified;

	private String bio;

	public static final String COLUMN_BIRTHDAY = "PRO_BIRTHDAY";
	@DatabaseField(columnName = COLUMN_BIRTHDAY, useGetSet = true)
	private String birthday;

	private String cover;

	private String currency;

	@JsonIgnore
	private transient List<Education> education;

	public static final String COLUMN_EMAIL = "PRO_EMAIL";
	@DatabaseField(columnName = COLUMN_EMAIL, useGetSet = true)
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

	public static final String COLUMN_RELATIONSHIP_STATUS = "PRO_RELATIONSHIP_STATUS";
	@DatabaseField(columnName = COLUMN_RELATIONSHIP_STATUS, useGetSet = true)
	private String relationshipStatus;

	private String religion;

	private String website;
	
	private Configuracoes configuracoes;

	@JsonIgnore
	private transient List<Work> work;

	public long getDbId() {
		return dbId;
	}

	public void setDbId(long dbId) {
		this.dbId = dbId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}

	public String getThirdPartyId() {
		return thirdPartyId;
	}

	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}

	public boolean isInstalled() {
		return installed;
	}

	public void setInstalled(boolean installed) {
		this.installed = installed;
	}

	public int getTimezone() {
		return timezone;
	}

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public List<Education> getEducation() {
		return education;
	}

	public void setEducation(List<Education> education) {
		this.education = education;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	public List<String> getFavoriteAthletes() {
		return favoriteAthletes;
	}

	public void setFavoriteAthletes(List<String> favoriteAthletes) {
		this.favoriteAthletes = favoriteAthletes;
	}

	public List<String> getFavoriteTeams() {
		return favoriteTeams;
	}

	public void setFavoriteTeams(List<String> favoriteTeams) {
		this.favoriteTeams = favoriteTeams;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getQuotes() {
		return quotes;
	}

	public void setQuotes(String quotes) {
		this.quotes = quotes;
	}

	public String getRelationshipStatus() {
		return relationshipStatus;
	}

	public void setRelationshipStatus(String relationshipStatus) {
		this.relationshipStatus = relationshipStatus;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public List<Work> getWork() {
		return work;
	}

	public void setWork(List<Work> work) {
		this.work = work;
	}

	public Configuracoes getConfiguracoes() {
		return configuracoes;
	}

	public void setConfiguracoes(Configuracoes configuracoes) {
		this.configuracoes = configuracoes;
	}
}
