package com.example.jason.route_application_kotlin.data.pojos.api;

import com.example.jason.route_application_kotlin.data.pojos.FormattedAddress;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jason on 14-Feb-18.
 */

public class SingleDrive implements Parcelable {

    private FormattedAddress originFormattedAddress;
    private FormattedAddress destinationFormattedAddress;

    private String status;

    private boolean destinationIsABusiness;

    private long driveDurationInSeconds;
    private String driveDurationHumanReadable;
    private long driveDistanceInMeters;
    private String driveDistanceHumanReadable;

    private long deliveryTimeInMillis;
    private String deliveryTimeHumanReadable;

    protected SingleDrive(Parcel in) {
        originFormattedAddress = in.readParcelable(FormattedAddress.class.getClassLoader());
        destinationFormattedAddress = in.readParcelable(FormattedAddress.class.getClassLoader());
        status = in.readString();
        destinationIsABusiness = in.readByte() != 0;
        driveDurationInSeconds = in.readLong();
        driveDurationHumanReadable = in.readString();
        driveDistanceInMeters = in.readLong();
        driveDistanceHumanReadable = in.readString();
        deliveryTimeInMillis = in.readLong();
        deliveryTimeHumanReadable = in.readString();
    }

    public static final Creator<SingleDrive> CREATOR = new Creator<SingleDrive>() {
        @Override
        public SingleDrive createFromParcel(Parcel in) {
            return new SingleDrive(in);
        }

        @Override
        public SingleDrive[] newArray(int size) {
            return new SingleDrive[size];
        }
    };

    public FormattedAddress getOriginFormattedAddress() {
        return originFormattedAddress;
    }

    public void setOriginFormattedAddress(FormattedAddress originFormattedAddress) {
        this.originFormattedAddress = originFormattedAddress;
    }

    public FormattedAddress getDestinationFormattedAddress() {
        return destinationFormattedAddress;
    }

    public void setDestinationFormattedAddress(FormattedAddress destinationFormattedAddress) {
        this.destinationFormattedAddress = destinationFormattedAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getDestinationIsABusiness() {
        return destinationIsABusiness;
    }

    public void setDestinationIsABusiness(boolean destinationIsABusiness) {
        this.destinationIsABusiness = destinationIsABusiness;
    }

    public long getDriveDurationInSeconds() {
        return driveDurationInSeconds;
    }

    public void setDriveDurationInSeconds(long driveDurationInSeconds) {
        this.driveDurationInSeconds = driveDurationInSeconds;
    }

    public String getDriveDurationHumanReadable() {
        return driveDurationHumanReadable;
    }

    public void setDriveDurationHumanReadable(String driveDurationHumanReadable) {
        this.driveDurationHumanReadable = driveDurationHumanReadable;
    }

    public long getDriveDistanceInMeters() {
        return driveDistanceInMeters;
    }

    public void setDriveDistanceInMeters(long driveDistanceInMeters) {
        this.driveDistanceInMeters = driveDistanceInMeters;
    }

    public String getDriveDistanceHumanReadable() {
        return driveDistanceHumanReadable;
    }

    public void setDriveDistanceHumanReadable(String driveDistanceHumanReadable) {
        this.driveDistanceHumanReadable = driveDistanceHumanReadable;
    }

    public long getDeliveryTimeInMillis() {
        return deliveryTimeInMillis;
    }

    public void setDeliveryTimeInMillis(long deliveryTimeInMillis) {
        this.deliveryTimeInMillis = deliveryTimeInMillis;
    }

    public String getDeliveryTimeHumanReadable() {
        return deliveryTimeHumanReadable;
    }

    public void setDeliveryTimeHumanReadable(String deliveryTimeHumanReadable) {
        this.deliveryTimeHumanReadable = deliveryTimeHumanReadable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(originFormattedAddress, flags);
        dest.writeParcelable(destinationFormattedAddress, flags);
        dest.writeString(status);
        dest.writeByte((byte) (destinationIsABusiness ? 1 : 0));
        dest.writeLong(driveDurationInSeconds);
        dest.writeString(driveDurationHumanReadable);
        dest.writeLong(driveDistanceInMeters);
        dest.writeString(driveDistanceHumanReadable);
        dest.writeLong(deliveryTimeInMillis);
        dest.writeString(deliveryTimeHumanReadable);
    }
}
