# Scalable Spring MVC Twitter Clone

A high-performance micro-blogging architecture built on Spring MVC and PostgreSQL, featuring secure session tracking, type-safe jOOQ database queries, and a self-joining social graph follower network.

## System Core Features

* **Asynchronous User Signup**: Processes background registration requests with active data-tier constraints using jQuery AJAX.
* **Cookie-Based Session Tracking**: Securely manages user states across sessions via custom interceptor filters and secure cookie token wrappers ("t").
* **Chronological Tweet Persistence**: Stores and aggregates multi-user status timelines using structured relational foreign keys.
* **Self-Joining Social Graph**: Implements a dedicated relationship bridging layout to process real-time follow and unfollow network states.
* **Multipart Image Upload Processing**: Configured to stream and update custom user profile media elements across dashboard feeds.

## Technical Architecture Stack

* **Backend Framework**: Java Spring MVC (Context & View Mappings)
* **Database Query Engine**: jOOQ (Type-Safe Compile-Time SQL Generation)
* **Database Server**: PostgreSQL Engine / pgAdmin 4
* **Build System**: Maven Lifecycle Management
* **Web Container Deployment**: Jetty Plugin Environment
* **Frontend UI Layout**: JavaServer Pages (JSP), Native JavaScript, CSS3

## Core Application Modules

* `/signup` - Handles user registration forms and entry boundaries.
* `/login/welcome` - Secure entry checkpoint validating profile credentials.
* `/user/welcome` - Assembles and displays the active home timeline feed.
* `/user/recommendations` - Computes and renders custom profile connection lists.
* `/user/followed` - Displays the relationship network of tracked accounts.

## Project Verification Technical Report
The complete technical architecture report, folder mappings, database schema scripts, and compilation execution timelines are available in the repository documentation file: [TwitterClone.docx](./TwitterClone.docx)
