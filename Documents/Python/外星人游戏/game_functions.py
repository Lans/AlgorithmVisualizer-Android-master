import sys
import pygame


def check_events(ship):
    # 响应按键和鼠标事件
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            sys.exit()
        elif event.type == pygame.KEYDOWN:
            check_keydown_events(event, ship)
        elif event.type == pygame.KEYUP:
            check_keyup_events(event, ship)


# 响应按下事件
def check_keydown_events(event, ship):
    if event.key == pygame.K_RIGHT:
        ship.moving_right = True
    elif event.key == pygame.K_LEFT:
        ship.moving_left = True


# 响应松开事件
def check_keyup_events(event, ship):
    if event.key == pygame.K_RIGHT:
        ship.moving_right = False
    elif event.key == pygame.K_LEFT:
        ship.moving_left = False


# 更新屏幕图像，并切换到新屏幕
def update_scree(ai_settings, scree, ship, love):
    # 填充屏幕颜色
    scree.fill(ai_settings.bg_color)
    # 创建图像
    ship.biltme()
    love.biltLove()
    # 让屏幕可见
    pygame.display.flip()
