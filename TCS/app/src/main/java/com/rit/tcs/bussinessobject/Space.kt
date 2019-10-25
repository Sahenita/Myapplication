package com.rit.tcs.bussinessobject

class Space {

    private var available_quantity: String? = null

    private var booked_quantity: String? = null

    private var outofstock: String? = null

    private var hourly_rate: String? = null

    private var id: String? = null

    private var type: String? = null

    private var isFadoClosed: Boolean = false

    private var release:String?=null

    fun getIsRealsed(): String? {
        return release
    }

    fun setIsRealsed(release: String) {
        this.release = release
    }


    fun getIsFadoClosed(): Boolean? {
        return isFadoClosed
    }

    fun setIsFadoClosed(isFadoClosed: Boolean) {
        this.isFadoClosed = isFadoClosed
    }

    fun getAvailable_quantity(): String? {
        return available_quantity
    }

    fun setAvailable_quantity(available_quantity: String) {
        this.available_quantity = available_quantity
    }

    fun getBooked_quantity(): String? {
        return booked_quantity
    }

    fun setBooked_quantity(booked_quantity: String) {
        this.booked_quantity = booked_quantity
    }

    fun getOutofstock(): String? {
        return outofstock
    }

    fun setOutofstock(outofstock: String) {
        this.outofstock = outofstock
    }

    fun getHourly_rate(): String? {
        return hourly_rate
    }

    fun setHourly_rate(hourly_rate: String) {
        this.hourly_rate = hourly_rate
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }

    private var billing_amount: String? = null

    private var pin: String? = null


    private var booking_date: String? = null

    private var totalhour: String? = null

    private var uniqueId: String? = null

    private var bookingId: String? = null

    private var release_time: String? = null

    fun getBilling_amount(): String? {
        return billing_amount
    }

    fun setBilling_amount(billing_amount: String) {
        this.billing_amount = billing_amount
    }

    fun getPin(): String? {
        return pin
    }

    fun setPin(pin: String) {
        this.pin = pin
    }

    fun getBooking_date(): String? {
        return booking_date
    }

    fun setBooking_date(booking_date: String) {
        this.booking_date = booking_date
    }

    fun getTotalhour(): String? {
        return totalhour
    }

    fun setTotalhour(totalhour: String) {
        this.totalhour = totalhour
    }


    fun getUniqueId(): String? {
        return uniqueId
    }

    fun setUniqueId(uniqueId: String) {
        this.uniqueId = uniqueId
    }

    fun getBookingId(): String? {
        return bookingId
    }

    fun setBookingId(bookingId: String) {
        this.bookingId = bookingId
    }

    fun getRelease_time(): String? {
        return release_time
    }

    fun setRelease_time(release_time: String) {
        this.release_time = release_time
    }
}