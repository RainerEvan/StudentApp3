import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { StudentService } from '../service/student.service';
import { Student } from '../student';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  public students: Student[] = [];
  public editStudent: Student;
  public deleteStudent: Student;
  public userFile: File;
  public imagePath: any;
  public imageUrl: any;

  constructor(private studentService: StudentService){}

  ngOnInit() {
      this.getStudents();
  }

  public getStudents(): void{
    this.studentService.getStudents().subscribe(
      (response: Student[]) => {
        this.students = response;
        console.log(this.students);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddStudent(addForm: NgForm): void{
    document.getElementById('add-student-form')!.click();
    const formData = new FormData();
    const student = addForm.value;

    formData.append('student',JSON.stringify(student));
    formData.append('imageUrl',this.userFile);
    
    this.studentService.addStudents(formData).subscribe(
      (response: Student) => {
        console.log(response);
        this.getStudents();
        addForm.reset();
        this.imageUrl = 0;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
        this.imageUrl = 0;
      }
    );
  }

  public onSelectFile(event:any){
    if(event.target.files.length > 0){
      const file = event.target.files[0];
      this.userFile = file;

      var reader = new FileReader();
      this.imagePath = file;
      reader.readAsDataURL(file);
      reader.onload = (_event) => {
        this.imageUrl = reader.result;
      }
    }
  }

  public onUpdateStudent(student: Student): void{
    this.studentService.updateStudents(student).subscribe(
      (response: Student) => {
        console.log(response);
        this.getStudents();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteStudent(studentId: number): void{
    this.studentService.deleteStudents(studentId).subscribe(
      (response: void) => {
        console.log(response);
        this.getStudents();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public searchStudents(key: string): void {
    console.log(key);
    const results: Student[] = [];
    for (const student of this.students){
      if (student.name.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || student.email.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || student.dob.toLowerCase().indexOf(key.toLowerCase()) !== -1
      ){
        results.push(student);
      }
    }
    this.students = results;
    if(results.length === 0 || !key){
      this.getStudents();
    }
  }

  public onOpenModal(student: Student, mode: string): void{
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle','modal');

    if (mode === 'add'){
      button.setAttribute('data-target','#addStudentModal');
    }
    if (mode === 'edit'){
      this.editStudent = student;
      button.setAttribute('data-target','#updateStudentModal');
    }
    if (mode === 'delete'){
      this.deleteStudent = student;
      button.setAttribute('data-target','#deleteStudentModal');
    }

    container!.appendChild(button);
    button.click();
  }

}
