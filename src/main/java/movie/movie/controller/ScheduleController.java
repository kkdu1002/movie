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

    //スケジュール登録画面表示
    @PostMapping("/home/schedule")
    public String initScheduleForm(@RequestParam("id") Long movieId , Model model , CreateScheduleDto dto) {

        dto.setMovieId(movieId);
        model.addAttribute("scheduleDto", dto);

        return "schedule";
    }

    //スケジュール登録
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

    //スケジュールリスト表示
    @GetMapping("/home/scheduleList")
    public String showScheduleList(Model model) {
        List<Schedule> scheduleList = scheduleService.findAll();

        model.addAttribute("scheduleList", scheduleList);
        return "scheduleList";
    }

    //映画のスケジュール確認画面表示
    @GetMapping("/home/insertSchedule")
    public String insertSchedule(Model model) {
        List<Schedule> scheduleList = scheduleService.findAll();

        model.addAttribute("scheduleData" , scheduleList);

        return "insertSchedule";
    }

    //スケジュール更新画面表示
    @GetMapping("/home/schedule/edit/{id}")
    public String updateShowSchedule(@PathVariable("id") Long id, Model model) {
        Schedule schedule = scheduleService.findOne(id);

        model.addAttribute("schedule", schedule);

        return "updateSchedule";
    }

    //スケジュール更新
    @PutMapping("/home/schedule/edit/{id}")
    @ResponseBody
    public void updateSchedule(@PathVariable Long id ,
                                 @RequestBody UpdateScheduleDto dto) {

        scheduleService.updateSchedule(id , dto);

    }
}
