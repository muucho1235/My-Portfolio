package model.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

//出退勤時刻管理モデルクラス。
public class WorkTime {
	//出勤日。
	private LocalDate workDate;
	//出勤時刻。
	private LocalTime startTime;
	//退勤時刻。
	private LocalTime finishTime;
	//休憩開始時刻。
	private LocalTime breakStartTime;
	//休憩終了時刻。
	private LocalTime breakFinishTime;
	//休憩時間。
	private Duration breakTime;
	//勤務時間。
	private Duration workingHours;
	//勤務時間を取得する。
	public LocalDate getWorkdate() {
		return workDate;
	}

	//勤務時間をセットする。
	public void setWorkDate(LocalDate workdate) {
		this.workDate = workdate;
	}

	//出勤時刻を取得する。
	public LocalTime getStartTime() {
		return startTime;
	}

	//出勤時刻をセットする。
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	//退勤時刻を取得する。
	public LocalTime getFinishTime() {
		return finishTime;
	}

	//退勤時間をセットする。
	public void setFinishTime(LocalTime finishTime) {
		this.finishTime = finishTime;
	}

	//休憩開始時間を取得する。
	public LocalTime getBreakStartTime() {
		return breakStartTime;
	}

	//休憩開始時をセットする。
	public void setBreakStartTime(LocalTime breakStartTime) {
		this.breakStartTime = breakStartTime;
	}

	//休憩終了時間を取得する。
	public LocalTime getBreakFinishTime() {
		return breakFinishTime;
	}

	//休憩終了時間をセットする。
	public void setBreakFinishTime(LocalTime breakFinishTime) {
		this.breakFinishTime = breakFinishTime;
	}

	//休憩時間を取得する。
	public Duration getBreakTime() {
		return breakTime;
	}

	//休憩時間をセットする。
	public void setBreakTime(Duration breakTime) {
		this.breakTime = breakTime;
	}

	//勤務時間を取得する。
	public Duration getWorkingHours() {
		return workingHours;
	}

	//勤務時間をセットする。
	public void setWorkingHours(Duration workingHours) {
		this.workingHours = workingHours;
	}

	//休憩開始時間と休憩終了時間から休憩時間を自動計算する。
	public void calcBreakTime() {
		Duration duration = Duration.between(breakStartTime, breakFinishTime);
		setBreakTime(duration);
	}

	/**
	 * 出勤時間と退勤時間から勤務時間を自動計算する。
	 * 休憩時間があるときは勤務時間から休憩時間を引く。
	 */
	public void calcWorkingHours() {
		Duration duration = Duration.between(startTime, finishTime);
		setWorkingHours(duration);
		if(breakTime != null) {
			duration = workingHours.minus(breakTime);
			setWorkingHours(duration);
		}
	}
}
