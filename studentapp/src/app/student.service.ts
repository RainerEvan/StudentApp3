import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Student } from './student';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private apiServerUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  public getStudents(): Observable<Student[]>{
    return this.http.get<Student[]>(this.apiServerUrl+'/api/student');
  }

  public addStudents(student: Student): Observable<Student>{
    return this.http.post<Student>(this.apiServerUrl+'/api/new',student);
  }

  public updateStudents(student: Student): Observable<Student>{
    return this.http.put<Student>(this.apiServerUrl+'/api/update',student);
  }

  public deleteStudents(studentId: number): Observable<void>{
    return this.http.delete<void>(this.apiServerUrl+'/api/del/'+studentId);
  }
}
