import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Student } from '../student';

const apiServerUrl = 'http://localhost:8080/api/student';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(private http: HttpClient) { }

  public getStudents(): Observable<Student[]>{
    return this.http.get<Student[]>(apiServerUrl);
  }

  public addStudents(formData: FormData): Observable<Student>{
    return this.http.post<any>(apiServerUrl+'/new',formData);
  }

  public updateStudents(student: Student): Observable<Student>{
    return this.http.put<Student>(apiServerUrl+'/update',student);
  }

  public deleteStudents(studentId: number): Observable<void>{
    return this.http.delete<void>(apiServerUrl+'/del/'+studentId);
  }
}
