package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task implements Comparable<Task> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Title is required")
	@Size(max = 150, message = "Title cannot exceed 100 characters")
	private String title;

	@Column
	private String description;

	@Column
	@Enumerated(EnumType.STRING)
	private LabelPriority label;

	@Column(name = "deadline")
	@Future(message = "Deadline must be in the future")
	private LocalDateTime deadline;

	@Column
	private LocalDateTime createdAt;
	
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

	@Column
	private boolean completed;

	// Constructors
	public Task() {
	}

	public Task(String title, String description, LabelPriority label, LocalDateTime deadline) {
		this.title = title;
		this.description = description;
		this.label = label;
		this.deadline = deadline;
		this.createdAt = LocalDateTime.now();
	}
	
	@PrePersist
	protected void onCreate() {
		if(label == null)  label = LabelPriority.NONE;
		if(createdAt == null)  createdAt = LocalDateTime.now();
		completed = false;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LabelPriority getLabel() {
		return label;
	}

	public void setLabel(LabelPriority label) {
		this.label = label;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	// Comparator logic for TreeSet ordering
	@Override
	public int compareTo(Task other) {
		// 1. Deadline (earliest first)
		if (this.deadline != null && other.deadline != null) {
			int deadlineCompare = this.deadline.compareTo(other.deadline);
			if (deadlineCompare != 0)
				return deadlineCompare;
		} else if (this.deadline != null) {
			return -1; // tasks with deadlines come before those without
		} else if (other.deadline != null) {
			return 1;
		}

		// 2. Label priority (Important > Medium > Low > None)
		int labelCompare = other.label.ordinal() - this.label.ordinal();
		if (labelCompare != 0)
			return labelCompare;

		// 3. Created time (oldest first)
		int createdCompare = this.createdAt.compareTo(other.createdAt);
		if (createdCompare != 0)
			return createdCompare;

		// 4. ID tie-breaker to prevent duplicates in TreeSet
		return Long.compare(this.id != null ? this.id : 0, other.id != null ? other.id : 0);
	}

	// Equals and hashCode for proper TreeSet + HashMap behavior
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Task))
			return false;
		Task task = (Task) o;
		return Objects.equals(id, task.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
