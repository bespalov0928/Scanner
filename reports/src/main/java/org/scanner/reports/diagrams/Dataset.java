package org.scanner.reports.diagrams;

import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.scanner.reports.dto.Ticket;

import java.util.Calendar;
import java.util.List;

public class Dataset {
    public static XYDataset createDataset() {
        final TimeSeries s1 = new TimeSeries("Курс USD");
        s1.add(new Day(11, 5, 2017), 58.0824);
        s1.add(new Day(12, 5, 2017), 57.1161);
        s1.add(new Day(13, 5, 2017), 57.1640);
        s1.add(new Day(16, 5, 2017), 56.5258);
        s1.add(new Day(17, 5, 2017), 56.2603);

        final TimeSeries s2 = new TimeSeries("Курс EUR");
        s2.add(new Day(11, 5, 2017), 63.2634);
        s2.add(new Day(12, 5, 2017), 62.1595);
        s2.add(new Day(13, 5, 2017), 62.0915);
        s2.add(new Day(16, 5, 2017), 61.8449);
        s2.add(new Day(17, 5, 2017), 62.0382);

        final TimeSeries s3 = new TimeSeries("Нефть марки Brent");
        s3.add(new Day(11, 5, 2017), 50.78);
        s3.add(new Day(12, 5, 2017), 50.82);
        s3.add(new Day(13, 5, 2017), 51.77);
        s3.add(new Day(16, 5, 2017), 51.30);
        s3.add(new Day(17, 5, 2017), 52.14);

        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);
        dataset.addSeries(s2);
        dataset.addSeries(s3);
        return dataset;
    }

    public static XYDataset getDataset(List<Ticket> ticketList) {
        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        final TimeSeries bybit = new TimeSeries("Bybit");
        Calendar cal = Calendar.getInstance();

        for (Ticket ticket : ticketList) {
            cal.setTime(ticket.getDateRequest()); // подставьте ваш объект Date вместо 'date'
//            Integer hours = cal.get(Calendar.HOUR_OF_DAY); // Получение времени в 24-часовом формате
            Integer hours = cal.get(Calendar.HOUR); // Получение времени в 12-часовом формате
            Integer minutes = cal.get(Calendar.MINUTE);

            Minute minute = new Minute(minutes, getHour(hours));
            Double value = ticket.getPrice();
            bybit.addOrUpdate(minute, value);
        }
//        bybit.add(new Minute(13, getHour(1)), 200.0);
//        bybit.add(new Minute(13, getHour(1)), 200.0);

        dataset.addSeries(bybit);

        return dataset;
    }

    public static Hour getHour(final int value) {
        return new Hour(value, getDay());
    }

    public static Day getDay() {
        return new Day(15, 8, 2017);
    }

//    public static XYDataset createSuppliersBids() {
//        final Hour hour = getHour(1);
//        final Hour hour1 = getHour(1);
//        final Hour hour2 = (Hour) hour1.next();
//
//        final TimeSeries series1 = new TimeSeries("Поставщик 1");
//        series1.add(new Minute(13, hour), 200.0);
//        series1.add(new Minute(14, hour), 195.0);
//
//        final TimeSeries series2 = new TimeSeries("Поставщик 2");
//        series2.add(new Minute(25, hour1), 185.0);
//        series2.add(new Minute(0, hour2), 175.0);
//        series2.add(new Minute(5, hour2), 170.0);
//
//        TimeSeriesCollection result = new TimeSeriesCollection();
//        result.addSeries(series1);
//        result.addSeries(series2);
//        return result;
//    }
}
