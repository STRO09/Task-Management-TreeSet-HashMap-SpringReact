package service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import model.Task;

@Component
public class TaskCollectionManager {

	private final Map<Long, Task> taskMap = new HashMap<>();
	private final TreeSet<Task> taskSet = new TreeSet<>(new TaskComparator());

	public void addTask(Task task) {
		taskMap.put(task.getId(), task);
		taskSet.add(task);
	}

	public void removeTask(Long id) {
		Task task = taskMap.remove(id);
		if (task != null) {
			taskSet.remove(task);
		}
	}

	public void updateTask(Task task) {
		removeTask(task.getId());
		addTask(task);
	}

	public Task getTask(Long id) {
		return taskMap.get(id);
	}

	public Collection<Task> getAllTasks() {
		return Collections.unmodifiableCollection(taskSet);
	}

	private static class TaskComparator implements Comparator<Task> {
		@Override
		public int compare(Task t1, Task t2) {
			if (t1.getDeadline() != null && t2.getDeadline() != null) {
				int cmp = t1.getDeadline().compareTo(t2.getDeadline());
				if (cmp != 0)
					return cmp;
			} else if (t1.getDeadline() != null)
				return -1;
			else if (t2.getDeadline() != null)
				return 1;

			int labelCmp = t1.getLabel().toString().compareToIgnoreCase(t2.getLabel().toString());
			if (labelCmp != 0)
				return labelCmp;

			return Long.compare(t1.getId(), t2.getId());
		}
	}
}
