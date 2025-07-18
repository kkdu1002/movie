package movie.movie.controller;

import lombok.RequiredArgsConstructor;
import movie.movie.domain.Movie;
import movie.movie.domain.Schedule;
import movie.movie.dto.CreateScheduleDto;
import movie.movie.dto.UpdateScheduleDto;
import movie.movie.service.MovieService;
import movie.movie.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ScheduleController {

    private final MovieService movieService;
    private final ScheduleService scheduleService;

    @PostMapping("/home/schedule")
    public String initScheduleForm(@RequestParam("id") Long movieId , Model model , CreateScheduleDto dto) {

        dto.setMovieId(movieId);
        model.addAttribute("scheduleDto", dto);

        return "schedule";
    }

    @PostMapping("/home/schedule/new")
    public String scheduleForm(@ModelAttribute CreateScheduleDto scheduleDto) {

        Movie movie = movieService.findOne(scheduleDto.getMovieId());

        Schedule schedule = Schedule.builder()
                .movie(movie)
                .theater(scheduleDto.getTheater())
                .screenTime(scheduleDto.getScreenTime())
                .availableSeats(scheduleDto.getAvailableSeats())
                .build();

        scheduleService.save(schedule);

        return "redirect:/home/schedule/scheduleList";
    }

    @GetMapping("/home/scheduleList")
    public String showScheduleList(Model model) {
        List<Schedule> scheduleList = scheduleService.findAll();

        model.addAttribute("scheduleList", scheduleList);
        return "scheduleList";
    }

    @GetMapping("/home/insertSchedule")
    public String insertSchedule(Model model) {
        List<Schedule> scheduleList = scheduleService.findAll();

        model.addAttribute("scheduleData" , scheduleList);

        return "insertSchedule";
    }

    @GetMapping("/home/schedule/edit/{id}")
    public String updateShowSchedule(@PathVariable("id") Long id, Model model) {
        Schedule schedule = scheduleService.findOne(id);

        model.addAttribute("schedule", schedule);

        return "updateSchedule";
    }

    @PutMapping("/home/schedule/edit/{id}")
    @ResponseBody
    public void updateSchedule(@PathVariable Long id ,
                                 @RequestBody UpdateScheduleDto dto) {

        scheduleService.updateSchedule(id , dto);

    }
}
