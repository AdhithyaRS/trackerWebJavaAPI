package com.milky.trackerWeb.repository;

import com.milky.trackerWeb.model.Login;
import com.milky.trackerWeb.response.LoginResponse;

public interface LoginCustomRepo {
	LoginResponse findByUname(Login login);

}

