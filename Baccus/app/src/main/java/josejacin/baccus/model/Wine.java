package josejacin.baccus.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Wine implements Serializable {
    private String mName = null;
    private String mType = null;
    private int mPhoto = 0;
    private String mCompanyName = null;
    private String mCompanyWeb = null;
    private String mNotes = null;
    private String mOrigin = null;
    private int mRating = 0; // 0 to 5
    private List<String> mGrapes = new LinkedList<>();

    public Wine(String name, String type, int photo, String companyName, String companyWeb, String notes, String origin, int rating) {
        mName = name;
        mType = type;
        mPhoto = photo;
        mCompanyName = companyName;
        mCompanyWeb = companyWeb;
        mNotes = notes;
        mOrigin = origin;
        mRating = rating;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public int getPhoto() {
        return mPhoto;
    }

    public void setPhoto(int photo) {
        mPhoto = photo;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        mCompanyName = companyName;
    }

    public String getCompanyWeb() {
        return mCompanyWeb;
    }

    public void setCompanyWeb(String companyWeb) {
        mCompanyWeb = companyWeb;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public String getOrigin() {
        return mOrigin;
    }

    public void setOrigin(String origin) {
        mOrigin = origin;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public void addGrape(String grape) {
        mGrapes.add(grape);
    }

    public int getGrapeCount() {
        return mGrapes.size();
    }

    public String getGrape(int index) {
        return mGrapes.get(index);
    }
}
