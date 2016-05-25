package com.example.mytabletest;

/**
 * @author daij
 * @version 1.0 状态实体
 */
public class WashingRoomPojo {

	private static final long serialVersionUID = -8379602526383806649L;
	public static final String TAG = "WashingRoomPojo";
	public int wrPojoId;
	public String wrAreaName;
	public String wrAreaNames;
	public CharSequence wrManMTNum;
	public String wrManDCNum;
	public String wrManXBCNum;
	public String wrManXBCNum1;
	public String wrManXBCNum12;

	public int getWrPojoId() {
		return wrPojoId;
	}

	public WashingRoomPojo setWrPojoId(int wrPojoId) {
		this.wrPojoId = wrPojoId;
		return this;
	}

	public String getWrAreaName() {
		return wrAreaName;
	}

	public WashingRoomPojo setWrAreaName(String wrAreaName) {
		this.wrAreaName = wrAreaName;
		return this;
	}
	public String getWrAreaNames() {
		return wrAreaNames;
	}

	public WashingRoomPojo setWrAreaNames(String wrAreaNames) {
		this.wrAreaNames = wrAreaNames;
		return this;
	}

	public CharSequence getWrManMT() {
		return wrManMTNum;
	}

	public WashingRoomPojo setWrManMT(CharSequence wrManMT) {
		this.wrManMTNum = wrManMT;
		return this;
	}

	public String getWrManDC() {
		return wrManDCNum;
	}

	public WashingRoomPojo setWrManDC(String wrManDC) {
		this.wrManDCNum = wrManDC;
		return this;
	}

	public String getWrManXBC() {
		return wrManXBCNum;
	}

	public WashingRoomPojo setWrManXBC(String wrManXBC) {
		this.wrManXBCNum = wrManXBC;
		return this;
	}

	public String getWrManXBC1() {
		return wrManXBCNum1;
	}

	public WashingRoomPojo setWrManXBC1(String wrManXBC1) {
		this.wrManXBCNum1 = wrManXBC1;
		return this;
	}

	public String getWrManXBC12() {
		return wrManXBCNum12;
	}

	public WashingRoomPojo setWrManXBC12(String wrManXBC12) {
		this.wrManXBCNum12 = wrManXBC12;
		return this;
	}


}
