/*
 * Copyright © 2022-2024 Lypxc (545685602@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.panxiaochao.holiday.core;

import io.github.panxiaochao.core.utils.Singleton;
import io.github.panxiaochao.core.utils.StringPools;
import io.github.panxiaochao.core.utils.date.LocalDateTimeUtil;
import io.github.panxiaochao.holiday.constants.HolidayConstant;
import io.github.panxiaochao.holiday.entity.Holiday;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

/**
 * <p>
 * 节假日客户端
 * </p>
 * <p>
 * 《国务院关于职工工作时间的规定》对工时制度作了修改，规定为：<br/>
 * 1、每一周的法定工作日为：5天(原则上：自每周一至周五，周六和周日为工休日——完全适用国有机关和事业单位，企业除外：企业单位可根据实际情况灵活安排周休息日)<br/>
 * 2、全年的法定工休日为：104天(12个月所有的周六和周日)；<br/>
 * 3、全年的法定节假日为：11天(元旦1天、春节3天、清明节1天、五一劳动节1天、端午节1天、中秋节1天、国庆节3天)；<br/>
 * 4、一年的法定工作日为：365天/年-104天/年法定工休日-11天/年法定节假日=250天；
 * 5、每一个季度的法定工作日为：250天/年法定工作日÷4个季度=62.50天；<br/>
 * 6、每一个月法定工作日为：250天/年法定工作日÷12个月=20.83天。<br/>
 * </p>
 *
 * @author Lypxc
 * @since 2024-04-02
 * @version 1.0
 */
@RequiredArgsConstructor
public class HolidayClient {

	/**
	 * LOGGER HolidayClient.class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HolidayClient.class);

	/**
	 * 是否是工作日
	 * @param day 日期
	 * @return true Or false
	 */
	public boolean isWeekday(String day) {
		checkDay(day);
		LocalDate localDate = praseLocalDate(day);
		int week = localDate.getDayOfWeek().getValue();
		return week != 6 && week != 7;
	}

	/**
	 * 是否是公休日（休息日）
	 * @param day 日期
	 * @return true Or false
	 */
	public boolean isPublicHoliday(String day) {
		checkDay(day);
		LocalDate localDate = praseLocalDate(day);
		int week = localDate.getDayOfWeek().getValue();
		return week == 6 || week == 7;
	}

	/**
	 * 是否是节假日
	 * @param day 日期
	 * @return true Or false
	 */
	public boolean isHoliday(String day) {
		checkDay(day);
		LocalDate localDate = praseLocalDate(day);
		int year = localDate.getYear();
		Holiday holiday = Singleton.INST.get(HolidayConstant.KEY_PREFIX + year);
		if (holiday == null) {
			LOGGER.error("没有对应年[{}]的数据，请升级或者自行维护数据！", year);
			return false;
		}
		return holiday.getDays().stream().anyMatch(f -> f.getDate().equals(day));
	}

	/**
	 * 获取某年节假日数据
	 * @param day 日期
	 * @return true Or false
	 */
	// public Holiday getHoliday(String day) {
	// checkDay(day);
	// LocalDate localDate = praseLocalDate(day);
	// int year = localDate.getYear();
	// Holiday holiday = Singleton.INST.get(HolidayConstant.KEY_PREFIX + year);
	// if (holiday == null) {
	// LOGGER.error("没有对应年[{}]的数据，请升级或者自行维护数据！", year);
	// return null;
	// }
	// return holiday;
	// }

	/**
	 * 是否需要补班
	 * @param day 日期
	 * @return true Or false
	 */
	public boolean isWorkDay(String day) {
		checkDay(day);
		LocalDate localDate = praseLocalDate(day);
		int year = localDate.getYear();
		Holiday holiday = Singleton.INST.get(HolidayConstant.KEY_PREFIX + year);
		if (holiday == null) {
			LOGGER.error("没有对应年[{}]的数据，请升级或者自行维护数据！", year);
			return false;
		}
		return holiday.getWorkdays().stream().anyMatch(f -> f.getDate().equals(day));
	}

	/**
	 * 获取某一年假期的天数
	 * @param year 年份
	 * @return 假期天数
	 */
	public int holidaysCount(String year) {
		Holiday holiday = Singleton.INST.get(HolidayConstant.KEY_PREFIX + year);
		if (holiday == null) {
			LOGGER.error("没有[{}]年的节假日期数据，请升级或者自行维护数据！", year);
			return 0;
		}
		return holiday.getDays().size();
	}

	/**
	 * 日期验证是否合法
	 * @param day 日期
	 */
	private void checkDay(String day) {
		if (!StringUtils.hasText(day) || day.split(StringPools.DASH).length != 3) {
			throw new IllegalArgumentException("Invalid day: " + day);
		}
	}

	/**
	 * 解析日期为LocalDate
	 * @param day 日期
	 * @return LocalDate
	 */
	private LocalDate praseLocalDate(String day) {
		String[] days = StringUtils.split(day, StringPools.SPACE);
		if (days != null) {
			day = days[0];
		}
		return LocalDateTimeUtil.stringToLocalDate(day);
	}

}
