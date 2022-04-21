(function($) { "use strict";

    $(function() {
        var header = $(".start-style");
        $(window).scroll(function() {
            var scroll = $(window).scrollTop();

            if (scroll >= 10) {
                header.removeClass('start-style').addClass("scroll-on");
            } else {
                header.removeClass("scroll-on").addClass('start-style');
            }
        });
    });

    //Animation

    $(document).ready(function() {
        $('body.hero-anime').removeClass('hero-anime');
    });

    //Menu On Hover

    $('body').on('mouseenter mouseleave','.nav-item',function(e){
        if ($(window).width() > 750) {
            var _d=$(e.target).closest('.nav-item');_d.addClass('show');
            setTimeout(function(){
                _d[_d.is(':hover')?'addClass':'removeClass']('show');
            },1);
        }
    });

    //Switch light/dark

    $("#switch").on('click', function () {
        if ($("body").hasClass("dark")) {
            $("body").removeClass("dark");
            $("#switch").removeClass("switched");
        }
        else {
            $("body").addClass("dark");
            $("#switch").addClass("switched");
        }
    });

})(jQuery);

/* Dropdown */
$(document).ready(function(){
    $("select.dropdown").change(function(){
        var value = $(this).children("option:selected").val();
        /* message for testing purposes */
        /*alert("You have selected - " + value);*/
    });
});

/* bootcamp page */
function showLecturers() {
    var lecturersTable = document.getElementById("lecturers");
    var lecturersActive = document.getElementById("active-l");
    var studentsTable = document.getElementById("students");
    var studentsActive = document.getElementById("active-s");
    var tasksTable = document.getElementById("tasks");
    var tasksActive = document.getElementById("active-t");

    if (lecturersTable.style.display === "none") {
        lecturersTable.style.display = "block";
        lecturersActive.classList.add('custom-tab-active');

        studentsTable.style.display = "none";
        studentsActive.classList.remove('custom-tab-active');

        tasksTable.style.display = "none";
        tasksActive.classList.remove('custom-tab-active');
    }
}

/* bootcamp page */
function showStudents() {
    var lecturersTable = document.getElementById("lecturers");
    var lecturersActive = document.getElementById("active-l");
    var studentsTable = document.getElementById("students");
    var studentsActive = document.getElementById("active-s");
    var tasksTable = document.getElementById("tasks");
    var tasksActive = document.getElementById("active-t");

    if (studentsTable.style.display === "none") {
        studentsTable.style.display = "block";
        studentsActive.classList.add('custom-tab-active');

        lecturersTable.style.display = "none";
        lecturersActive.classList.remove('custom-tab-active');

        tasksTable.style.display = "none";
        tasksActive.classList.remove('custom-tab-active');
    }
}

/* bootcamp page */
function showTasks() {
    var lecturersTable = document.getElementById("lecturers");
    var lecturersActive = document.getElementById("active-l");
    var studentsTable = document.getElementById("students");
    var studentsActive = document.getElementById("active-s");
    var tasksTable = document.getElementById("tasks");
    var tasksActive = document.getElementById("active-t");

    if (tasksTable.style.display === "none") {
        tasksTable.style.display = "block";
        tasksActive.classList.add('custom-tab-active');

        studentsTable.style.display = "none";
        studentsActive.classList.remove('custom-tab-active');

        lecturersTable.style.display = "none";
        lecturersActive.classList.remove('custom-tab-active');
    }
}

/* students page */
function showLecturerBootcamps() {
    var bootcampsTable = document.getElementById("bootcamps");
    var bootcampsActive = document.getElementById("active-b");
    var studentsTable = document.getElementById("students");
    var studentsActive = document.getElementById("active-s");

    if (bootcampsTable.style.display === "none") {
        bootcampsTable.style.display = "block";
        bootcampsActive.classList.add('custom-tab-active');

        studentsTable.style.display = "none";
        studentsActive.classList.remove('custom-tab-active');
    }
}

/* students page */
function showLecturerBootcampStudents() {
    var bootcampsTable = document.getElementById("bootcamps");
    var bootcampsActive = document.getElementById("active-b");
    var studentsTable = document.getElementById("students");
    var studentsActive = document.getElementById("active-s");

    if (studentsTable.style.display === "none") {
        studentsTable.style.display = "block";
        studentsActive.classList.add('custom-tab-active');

        bootcampsTable.style.display = "none";
        bootcampsActive.classList.remove('custom-tab-active');
    }
}

$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})