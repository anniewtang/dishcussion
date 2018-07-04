<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<!DOCTYPE html>
<html>
<head>
    <title>CodeU Chat App</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>

<nav>
    <a href="/">Homepage</a>
    <% if (request.getSession().getAttribute("user") != null) { %>
    <a href="/profile"><%= request.getSession().getAttribute("user") %>'s Profile</a>
    <% } else { %>
    <a href="/login">Login</a>
    <% } %>
    <!--a href="/conversations">Conversations</a-->
    <a href="/about.jsp">About</a>
</nav>

<div id="container">
    <div style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">

        <h1>Dishcussion</h1>

        <!-- ADD SEARCH BAR -->
        <input id="user-entry" type="text" name="search" size="50" placeholder="What are you craving?">


        <form action="/results" method="get">
            <input id="go" type="submit" class="button" value="Go">
        </form>


        <br>

        <!-- ADD REVIEW BUTTON -->
        <form action="/review" method="get">
            <input id="add-review" type="submit" class="button" value="Add Review">
        </form>

        <!-- IF user is signed in... use recommendations based on history & current location -->
        <!-- IF user is not signed in... use recommendations based on current location -->

    </div>
</div>
</body>
</html>
