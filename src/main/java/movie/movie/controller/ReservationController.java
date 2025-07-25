package movie.movie.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import movie.movie.domain.Reservation;
import movie.movie.domain.Schedule;
import movie.movie.domain.User;
import movie.movie.dto.CreateReservationDto;
import movie.movie.service.ReservationService;
import movie.movie.service.ScheduleService;
import movie.movie.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final ScheduleService scheduleService;
    private final UserService userService;

    //予約情報表示
    @GetMapping("/home/reservation")
    public String initReservationAdmin(HttpSession session ,Model model) {

        Long userid = (Long) session.getAttribute("userid");

        User user = userService.findOne(userid);
        List<Reservation> reservationList;

        //管理者は全件検索
        if(user.getRole().equals("2")) {
            reservationList = reservationService.findAll();
        }
        else {
            reservationList = reservationService.findUser(user.getUsername());
        }

        model.addAttribute("movieTime" , LocalDateTime.now());
        model.addAttribute("reservationList",reservationList);

        return "reservation";
    }

    //予約画面表示
    @GetMapping("/home/insReservation")
    public String initReservation(HttpSession session , @RequestParam("id") Long scheduleId ,
                                  CreateReservationDto dto , Model model) {
        Long userid = (Long) session.getAttribute("userid");

        User user = userService.findOne(userid);
        Schedule schedule = scheduleService.findOne(scheduleId);

        dto.setUserId(user.getId());
        dto.setScheduleId(schedule.getId());

        model.addAttribute("schedule" , schedule);

        model.addAttribute("reservationDto" , dto);

        return "insReservation";
    }

    //予約処理
    @PostMapping("/home/insReservation")
    public String createReservation(@ModelAttribute("reservationDto") @Valid CreateReservationDto reservationDto ,
                                    BindingResult bindingResult ,
                                    Model model) {

        User user = userService.findOne(reservationDto.getUserId());
        Schedule schedule = scheduleService.findOne(reservationDto.getScheduleId());

        if(bindingResult.hasErrors()) {
            model.addAttribute("schedule" , schedule);
            return "insReservation";
        }

        Reservation reservation = Reservation.builder()
                .user(user)
                .schedule(schedule)
                .seatNumber(reservationDto.getSeatNumber())
                .build();

        reservationService.save(reservation);
        return "redirect:/home/insertSchedule";
    }

    @DeleteMapping("/home/reservation/edit/{reservationId}")
    @ResponseBody
    public void cancelReservation(@PathVariable("reservationId") Long id) {
        reservationService.deleteReservation(id);
    }

}
