package com.example.zeroday.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Budget extends ZeroBaseModel implements Parcelable {
    
    private String codeBudget;
    private String startDateBuget;
    private String endDateBuget;
    private Frequency frequency;

    public Budget() {
    }

    public Budget( String codeBudget, String startDateBuget, String endDateBuget, Frequency frequency) {
        this.codeBudget = codeBudget;
        this.startDateBuget = startDateBuget;
        this.endDateBuget = endDateBuget;
        this.frequency = frequency;
    }

    public Budget(Long id, String codeBudget, String startDateBuget, String endDateBuget, Frequency frequency) {
        this.id = id;
        this.codeBudget = codeBudget;
        this.startDateBuget = startDateBuget;
        this.endDateBuget = endDateBuget;
        this.frequency = frequency;
    }

    protected Budget(Parcel in) {
        codeBudget = in.readString();
        startDateBuget = in.readString();
        endDateBuget = in.readString();
    }

    public static final Creator<Budget> CREATOR = new Creator<Budget>() {
        @Override
        public Budget createFromParcel(Parcel in) {
            return new Budget(in);
        }

        @Override
        public Budget[] newArray(int size) {
            return new Budget[size];
        }
    };

    public String getCodeBudget() {
        return codeBudget;
    }

    public void setCodeBudget(String codeBudget) {
        this.codeBudget = codeBudget;
    }

    public String getStartDateBuget() {
        return startDateBuget;
    }

    public void setStartDateBuget(String startDateBuget) {
        this.startDateBuget = startDateBuget;
    }

    public String getEndDateBuget() {
        return endDateBuget;
    }

    public void setEndDateBuget(String endDateBuget) {
        this.endDateBuget = endDateBuget;
    }

    public Frequency getFrequence() {
        return frequency;
    }

    public void setFrequence(Frequency frequency) {
        this.frequency = frequency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(codeBudget);
        parcel.writeString(startDateBuget);
        parcel.writeString(endDateBuget);
    }

    @Override
    public String toString() {
        return "Budget{" +
                "codeBudget='" + codeBudget + '\'' +
                ", startDateBuget='" + startDateBuget + '\'' +
                ", endDateBuget='" + endDateBuget + '\'' +
                ", frequency=" + frequency +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget = (Budget) o;
        return getCodeBudget().equals(budget.getCodeBudget()) && getStartDateBuget().equals(budget.getStartDateBuget()) && getEndDateBuget().equals(budget.getEndDateBuget()) && frequency == budget.frequency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodeBudget(), getStartDateBuget(), getEndDateBuget(), frequency);
    }
}
