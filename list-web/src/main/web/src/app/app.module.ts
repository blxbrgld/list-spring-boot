import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { CommentFormComponent } from './components/comment-form/comment-form.component';
import { CommentsComponent } from './components/comments/comments.component';
import { SubtitleFormComponent } from './components/subtitle-form/subtitle-form.component';
import { SubtitlesComponent } from './components/subtitles/subtitles.component';
import { NotFoundComponent } from './components/not-found/not-found.component';

import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { AlertComponent } from './components/alert/alert.component';

// Application routes
const routes: Routes = [
  { path: '', redirectTo: '/subtitles', pathMatch: 'full' },
  { path: 'comments', component: CommentsComponent },
  { path: 'comments/create', component: CommentFormComponent },
  { path: 'comments/:id', component: CommentFormComponent },
  { path: 'subtitles', component: SubtitlesComponent },
  { path: 'subtitles/create', component: SubtitleFormComponent },
  { path: 'subtitles/:id', component: SubtitleFormComponent },
  { path: '**', component: NotFoundComponent }
];

/**
 * AppModule
 * @author blxbrgld
 */
@NgModule({
  declarations: [
    AlertComponent,
    AppComponent,
    CommentFormComponent,
    CommentsComponent,
    SubtitleFormComponent,
    SubtitlesComponent,
    NotFoundComponent
  ],
  imports: [
    AngularFontAwesomeModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
