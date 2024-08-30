package model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDate completion_date;
}
