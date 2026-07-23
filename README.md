# Scalable Spring MVC Twitter Clone

A full-stack Twitter-like web application built using Java Spring MVC and PostgreSQL, featuring secure user authentication, session management, tweet publishing, follow/unfollow functionality, personalized recommendations, and profile image uploads.

## Technologies

Backend: Java, Spring MVC

Frontend: JSP, HTML, CSS, JavaScript, jQuery, AJAX, Bootstrap

Database: PostgreSQL using jOOQ

Build Tool: Maven

Deployment: Jetty Plugin

## System Core Features

Asynchronous User Signup: Processes background registration requests using jQuery AJAX.

Cookie-Based Session Tracking: Securely manages authenticated user sessions using cookies and interceptor-based validation.

Tweet Persistence: Stores and retrieves user tweets using PostgreSQL relational tables.

Follow/Unfollow Network: Implements a self-referencing follower relationship model for managing social connections.

Personalized Home Feed: Displays tweets from followed users after successful authentication.

User Recommendation System: Suggests users to follow based on the existing social graph.

Multipart Profile Image Upload: Supports uploading and displaying user-specific profile pictures.

Custom Error Handling: Redirects invalid application routes to a dedicated alien.jsp error page.

## Technical Architecture Stack

Backend Framework: Java Spring MVC

Database Query Engine: jOOQ

Database Server: PostgreSQL

Frontend: JSP, HTML, CSS, JavaScript, jQuery, Bootstrap

Build Tool: Maven

Deployment: Jetty Plugin

## Core Application Modules

/ signup - User Registration

/ login - User Authentication

/ user/welcome - Personalized Home Timeline

/ user/recommendations - User Recommendation Page

/ user/followed - Following-by-Me Page

## Project Verification Technical Report

The repository contains **TwitterClone.pdf**, which includes:

- Complete System Architecture
- Database Schema
- Folder Structure
- User Interface Screenshots
- Project Workflow
- Implementation Details
