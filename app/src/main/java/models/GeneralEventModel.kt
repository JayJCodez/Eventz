package models

class GeneralEventModel {

    private var like_count: Int? = null
    private var eventid: Int? = null
    private var eventname: String = ""
    private var eventtype: String = ""
    private var imgUri: String = ""
    private var eventstarttime: String = ""
    private var eventfinishtime: String = ""
    private var eventdescription: String = ""
    private var eventdate: String = ""

    // Setter methods
    fun setEventId(id: Int?) {
        this.eventid = id
    }

    fun setEventName(name: String) {
        this.eventname = name
    }

    fun setEventType(type: String) {
        this.eventtype = type
    }

    fun setLikeCount(count: Int){
        this.like_count = count
    }
//    fun setImageURI(uri: String){
//        this.imgUri = uri
//    }

    fun setEventStartTime(startTime: String) {
        this.eventstarttime = startTime
    }

    fun setEventFinishTime(finishTime: String) {
        this.eventfinishtime = finishTime
    }

    fun setEventDescription(eventdescription: String) {
        this.eventdescription = eventdescription
    }

    fun setEventDate(eventdate: String) {
        this.eventdate = eventdate
    }

    fun getEventDate() : String {
        return this.eventdate
    }

    // Getter methods
//    fun getImageURI(): String {
//        return this.imgUri
//    }

    fun getEventId(): Int? {
        return this.eventid
    }

    fun getEventName(): String {
        return this.eventname
    }

    fun getEventType(): String {
        return this.eventtype
    }

    fun getEventStartTime(): String {
        return this.eventstarttime
    }

    fun getEventFinishTime(): String {
        return this.eventfinishtime
    }

    fun getEventDescription(): String {
        return this.eventdescription
    }

    fun getLikeCount(): Int? {

        return this.like_count

    }

//    override fun describeContents(): Int {
//        TODO("Not yet implemented")
//    }
//
//    override fun writeToParcel(dest: Parcel, flags: Int) {
//        TODO("Not yet implemented")
//    }
}