package tasks;

import java.util.Date;
import java.util.Calendar;

public class RecurringTask extends BaseTask {
    private int recurrencePeriod;

    public RecurringTask (String title, String description, Date dueDate, String status, Priority priority, int recurrencePeriod) {
        super(title, description, dueDate, status, priority);
        this.recurrencePeriod = recurrencePeriod;
    }

    @Override
    public void completeTask() {
        super.completeTask();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.getDueDate());
        calendar.add(Calendar.DATE, recurrencePeriod);

        this.setDueDate(calendar.getTime());

        this.setStatus("pending");

        System.out.println("This task must be completed again on " + this.getDueDate());
    }
}
